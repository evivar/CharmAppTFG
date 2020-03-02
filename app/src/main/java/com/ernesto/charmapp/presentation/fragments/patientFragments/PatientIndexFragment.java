package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Diary;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;
import com.ernesto.charmapp.presentation.dialogs.InfoDialog;

import java.sql.Date;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TODO: Falta hacer lo de la temperatura, el estado del cielo y las precipitaciones
 */
public class PatientIndexFragment extends Fragment {

    // Falta lo del resumen

    private Patient patient;

    private Button diaryBtn;

    private Button newAttackBtn;

    private Button historyBtn;

    private boolean activeCrisis;

    private boolean isDiaryFilled;

    private String dateString;

    public static PatientIndexFragment create(Patient patient) {
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        PatientIndexFragment f = new PatientIndexFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        patient = (Patient) arguments.getSerializable("patient");
        this.checkIfDiaryIsFilled();
        this.createDiaryAlarm();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_index, container, false);

        diaryBtn = v.findViewById(R.id.diarioBtn_patientMain);
        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<DiaryResponse> readLastDiary = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .readLastDiary(patient.getPatientId());
                readLastDiary.enqueue(new Callback<DiaryResponse>() {
                    @Override
                    public void onResponse(Call<DiaryResponse> call, Response<DiaryResponse> response) {
                        try {
                            DiaryResponse diaryResponse = response.body();
                            Log.d("Rellenar diario", response.body().getMensaje());
                            if (diaryResponse != null && !diaryResponse.getError()) {
                                Diary lastDiary = diaryResponse.getDiary();
                                Date date = new Date(System.currentTimeMillis());
                                if (!lastDiary.getDate().equals(date.toString())) {
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                            .addToBackStack(null)
                                            .replace(R.id.fragmentContainer_patient, DiaryFragment.create(patient, lastDiary), "DIARY_FRAGMENT")
                                            .commit();
                                } else {
                                    InfoDialog dialog = new InfoDialog("Ya ha rellenado el diario de hoy");
                                    dialog.show(getFragmentManager(), "tagAlerta");
                                }
                            } else {
                                Toast.makeText(getActivity(), "Primer diario del paciente", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, DiaryFragment.create(patient, new Diary()), "DIARY_FRAGMENT")
                                        .commit();
                            }
                        } catch (Exception e) {
                            Log.e("Excepcion rellenar", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<DiaryResponse> call, Throwable t) {

                    }
                });

            }
        });

        newAttackBtn = v.findViewById(R.id.nuevaCrisisBtn_patientMain);
        newAttackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Call<CrisisResponse> readActiveCrisisById = RetrofitClient.getInstance().getAPI().readActiveCrisisById(patient.getPatientId());
                readActiveCrisisById.enqueue(new Callback<CrisisResponse>() {
                    @Override
                    public void onResponse(Call<CrisisResponse> call, Response<CrisisResponse> response) {
                        CrisisResponse crisisResponse = response.body();
                        if (!crisisResponse.getError()) {
                            if (crisisResponse.getCrisis() == null) {
                                Log.i("Crear/Editar crisis", "Nueva crisis");
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(new Headache(), patient, false), "HEADACHE_FRAGMENT")
                                        .commit();
                            } else {
                                Log.i("Crear/Editar crisis", "Editar crisis");
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(crisisResponse.getCrisis(), patient, true), "HEADACHE_FRAGMENT")
                                        .commit();
                            }
                        } else {
                            // Falla la lectura
                            Toast.makeText(getContext(), "ERROR EN LA LECTURA DEL HEADACHE", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CrisisResponse> call, Throwable t) {

                    }
                });
            }
        });

        historyBtn = v.findViewById(R.id.historialBtn_patientMain);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_patient, HistoryFragment.create(patient), "HISTORY_FRAGMENT")
                        .commit();
            }
        });


        return v;
    }

    private void checkIfDiaryIsFilled() {
        Call<DiaryResponse> readLastDiary = RetrofitClient
                .getInstance()
                .getAPI()
                .readLastDiary(patient.getPatientId());
        readLastDiary.enqueue(new Callback<DiaryResponse>() {
            @Override
            public void onResponse(Call<DiaryResponse> call, Response<DiaryResponse> response) {
                try {
                    DiaryResponse diaryResponse = response.body();
                    Log.d("Respuesta del diario", response.body().getMensaje());
                    if (diaryResponse != null && !diaryResponse.getError()) {
                        isDiaryFilled = true;
                        Diary lastDiary = diaryResponse.getDiary();
                        Date date = new Date(System.currentTimeMillis());
                        if (!lastDiary.getDate().equals(date.toString())) {
                            isDiaryFilled = false;
                            Toast.makeText(getActivity(), "No te olvides de rellenar el diario", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        isDiaryFilled = false;
                        Toast.makeText(getActivity(), "No te olvides de rellenar el diario", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    Log.e("Excepción diario", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DiaryResponse> call, Throwable t) {

            }
        });
    }

    private void createDiaryAlarm() {
        if (!isDiaryFilled) {
            int flag = 1;
            double freq = 0.0;
            int type = 1;
            int freqMillis = (int) (freq * 60 * 60 * 1000);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 10);
            c.set(Calendar.MINUTE, 00);
            c.set(Calendar.SECOND, 0);

            Log.d("Alarma de Diario creada", "setalarm freq(hours) = " + freq + " -> alarm @ " +
                    c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + " " +
                    c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" +
                    c.get(Calendar.SECOND));


            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }

        }
    }

    /*
    Comprobar si hay una alarma activa
        La hay -> No hago nada
        No la hay -> Creo la alarma
     */
    /*private void createDiaryAlarm() {
        int flag = 1;
        double freq = 0.01;
        int type = 1; // No estoy seguro de que es esto
        int freqMillis = (int) (freq * 60 * 60 * 1000);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 10); // Poner a la hora, en formato 24h, a la que lanzar la alarma -> 10
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);
        // Esto lo pone en el codigo Notificaciones.java que pasaron por el mail
        // c.add(Calendar.DAY_OF_YEAR, 1);

        // Lo mostramos por el log
        Log.d("Alarma de Diario creada", "setalarm freq(hours) = " + freq + " -> alarm @ " +
                c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + " " +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" +
                c.get(Calendar.SECOND));

        // Creamos el alarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Creamos el intent
        Intent intent = new Intent(this, NotificationReceiver.class);
        // Creamos el pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Configuramos que se repita la alarma cada freqMillis
        // TODO: Algo falla al poner o el repeating o el c.add(Calendar.DAY_OF_YEAR, freq);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }*/

}
