package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.ernesto.charmapp.data.location.LocationAlertReceiver;
import com.ernesto.charmapp.data.notifications.DiaryAlertReceiver;
import com.ernesto.charmapp.data.notifications.DiaryNotificationHelper;
import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.domain.retrofitEntities.Diary;
import com.ernesto.charmapp.domain.retrofitEntities.Headache;
import com.ernesto.charmapp.domain.retrofitEntities.Patient;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;

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

    private boolean isDiaryFilled = true;

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
        this.startLocationService();
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
                            if (diaryResponse.getDiary() != null && !diaryResponse.getError()) {
                                Diary lastDiary = diaryResponse.getDiary();
                                Date date = new Date(System.currentTimeMillis());
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, DiaryFragment.create(patient, lastDiary), "DIARY_FRAGMENT")
                                        .commit();
                                /*if (!lastDiary.getDate().equals(date.toString())) {
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                            .addToBackStack(null)
                                            .replace(R.id.fragmentContainer_patient, DiaryFragment.create(patient, lastDiary), "DIARY_FRAGMENT")
                                            .commit();
                                } else {
                                    InfoDialog dialog = new InfoDialog("Ya ha rellenado el diario de hoy");
                                    dialog.show(getFragmentManager(), "tagAlerta");
                                }*/
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
                    if (diaryResponse.getDiary() != null && !diaryResponse.getError()) {
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
                    Log.e("Excepción diario2  ", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DiaryResponse> call, Throwable t) {

            }
        });
    }

    private void createDiaryAlarm() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 10);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        if (isDiaryFilled) {
            c.add(Calendar.DATE, 1);
        }
        startDiaryAlarm(c);

    }

    private void startDiaryAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), DiaryAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 1, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        DiaryNotificationHelper.enableBootReceiver(this.getContext());
    }

    private void startLocationService() {
        //StationViewModel stationViewModel = ViewModelProviders.of(this).get(StationViewModel.class);
        LocationAlertReceiver locationAlertReceiver = new LocationAlertReceiver(/*stationViewModel*/);
        locationAlertReceiver.setAlarm(this.getContext());
        LocationAlertReceiver.enableBootReceiver(this.getContext());
    }

}
