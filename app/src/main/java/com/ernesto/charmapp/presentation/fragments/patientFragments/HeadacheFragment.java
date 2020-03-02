package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.UpdateResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.validators.HeadacheValidator;
import com.ernesto.charmapp.presentation.activities.MainActivity;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientMainActivity;
import com.ernesto.charmapp.presentation.dialogs.DateDialog;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeadacheFragment extends Fragment {

    private static final String NUEVA_CRISIS = "Nueva crisis";

    private static final String EDITAR_CRISIS = "Editar crisis";

    private Headache headache;

    private Patient patient;

    private boolean editing;

    private HeadacheValidator validator;

    private TextView header;

    private EditText startDateTxt;

    private String startDate;

    private EditText endDateTxt;

    private String endDate;

    private Spinner sportSpinner;

    private String sport;

    private Spinner alcoholSpinner;

    private String alcohol;

    private Spinner smokeSpinner;

    private String smoke;

    private Spinner medicationSpinner;

    private String medication;

    private EditText feelingTxt;

    private String feeling;

    private Spinner painScaleSpinner;

    private int painScale;

    private Button saveBtn;

    public static HeadacheFragment create(Headache headache, Patient patient, boolean editing) {
        Bundle args = new Bundle();
        args.putSerializable("headache", headache);
        args.putSerializable("patient", patient);
        args.putSerializable("editing", editing);
        HeadacheFragment f = new HeadacheFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        headache = (Headache) arguments.getSerializable("headache");
        patient = (Patient) arguments.getSerializable("patient");
        editing = (boolean) arguments.getSerializable("editing");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_headache, container, false);
        // Inizializar los campos
        header = v.findViewById(R.id.headerTitleLbl_headache);
        startDateTxt = v.findViewById(R.id.startDateTxt_headache);
        endDateTxt = v.findViewById(R.id.endDateTxt_headache);
        sportSpinner = v.findViewById(R.id.sportSpinner_headache);
        alcoholSpinner = v.findViewById(R.id.alcoholSpinner_headache);
        smokeSpinner = v.findViewById(R.id.smokeSpinner_headache);
        medicationSpinner = v.findViewById(R.id.medicationSpinner_headache);
        feelingTxt = v.findViewById(R.id.feelingTxt_headache);
        painScaleSpinner = v.findViewById(R.id.painScaleSpinner_headache);
        saveBtn = v.findViewById(R.id.saveBtn_headache);

        if (editing) {
            header.setText(EDITAR_CRISIS);
            fillFields();
        } else {
            header.setText(NUEVA_CRISIS);
            createCrisisAlarm();
        }

        startDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(startDateTxt);
            }
        });
        endDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(endDateTxt);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new HeadacheValidator();
                // Valida los campos
                if (validateFields()) {
                    if ((endDate.isEmpty()) || (endDate.length() != 10) || (endDate == null)) {
                        endDate = "0000-00-00";
                    }
                    // Llama al api para meter el headache
                    if (editing) {
                        // Solo se updatea
                        final Call<UpdateResponse> updateCrisis = RetrofitClient
                                .getInstance()
                                .getAPI()
                                .updateCrisis(patient.getPatientId(), startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
                        updateCrisis.enqueue(new Callback<UpdateResponse>() {
                            @Override
                            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                                UpdateResponse updateResponse = response.body();
                                if (!updateResponse.getError()) {
                                    Toast.makeText(getActivity(), "Crisis actualizada correctamente", Toast.LENGTH_LONG).show();
                                    if (endDate != "0000-00-00") {
                                        //cancelAlarm();
                                        Toast.makeText(getActivity(), "Notificaciones de crisis desactivadas", Toast.LENGTH_SHORT).show();
                                    }
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                            .addToBackStack(null)
                                            .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "PATIENT_INDEX_FRAGMENT")
                                            .commit();
                                } else {
                                    Toast.makeText(getActivity(), updateResponse.getMensaje(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateResponse> call, Throwable t) {

                            }
                        });
                    }
                    if (!editing) {
                        final Call<CrisisResponse> createCrisis = RetrofitClient
                                .getInstance()
                                .getAPI()
                                .createCrisis(patient.getPatientId(), startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
                        createCrisis.enqueue(new Callback<CrisisResponse>() {
                            @Override
                            public void onResponse(Call<CrisisResponse> call, Response<CrisisResponse> response) {
                                CrisisResponse crisisResponse = response.body();
                                if (crisisResponse != null && !crisisResponse.getError()) {
                                    Log.i("Crisis", "Crisis creada correctamente");
                                    Toast.makeText(getActivity(), "Crisis creada correctamente", Toast.LENGTH_LONG).show();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                            .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "PATIENT_INDEX_FRAGMENT")
                                            .addToBackStack(null)
                                            .commit();
                                } else {
                                    Toast.makeText(getActivity(), "ERROR: No se pudo crear la crisis", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CrisisResponse> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    // Mostramos un dialogo de error con los campos a revisar y ademas los ponemos como error
                    showErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
                }
            }
        });


        return v;
    }

    public void showDatePickerDialog(final EditText dateTxt) {
        DateDialog newFragment = DateDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque Enero es el 0
                final String selectedDate = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                try {
                    Date datePickerDate = new SimpleDateFormat("dd-MM-yyyy").parse(selectedDate);
                    if (datePickerDate.before(new Date())) {
                        showErrorDialog("Error: La fecha no puede ser posterior a hoy", "Seleccione una fecha anterior");
                    } else {
                        dateTxt.setText(selectedDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public boolean validateFields() {
        return validator.validate(startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
    }

    public void showErrorDialog(String title, String msg) {
        ErrorDialog errorDialog = new ErrorDialog(title, msg);
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    private void createCrisisAlarm() {
        int flag = 1;
        int freq = patient.getCh_duration();
        int type = 1; // No estoy seguro de que es esto
        int freqMillis = (int) (freq * 60 * 60 * 1000);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 22); // Poner a la hora, en formato 24h, a la que lanzar la alarma -> 10
        c.set(Calendar.MINUTE, 39);
        c.set(Calendar.SECOND, 0);
        // Esto lo pone en el codigo Notificaciones.java que pasaron por el mail
        c.add(Calendar.DAY_OF_YEAR, freq);

        // Lo mostramos por el log
        Log.d("Alarma de Crisis creada", "setalarm freq(hours) = " + freq + " -> alarm @ " +
                c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + " " +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" +
                c.get(Calendar.SECOND));

        // Creamos el alarmManager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // Creamos el intent
        Intent intent = new Intent(getContext(), (this.patient.getPatientId() != null) ? PatientMainActivity.class : MainActivity.class);
        // Creamos el pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Configuramos que se repita la alarma cada freqMillis
        // TODO: Algo falla al poner o el repeating o el c.add(Calendar.DAY_OF_YEAR, freq);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), freqMillis, pendingIntent);
    }

    // TODO: Creo otra vez los metodos de la alarma en el fragment de las crisis, cuando se crea una crisis llamo al createAlarm y cuando se modifica para meter una fecha final llamo al cancelAlarm
    /*private void cancelAlarm() {
        // Creamos el alarmManager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // Creamos el intent
        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        // Creamos el pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Cancelamos el alarmManager
        alarmManager.cancel(pendingIntent);
    }*/

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void fillFields() {
        this.startDateTxt.setText(headache.getStartDatetime().substring(0, 10));
        if (headache.getSport().equals("Sí")) {
            this.sportSpinner.setSelection(1);
        } else {
            this.sportSpinner.setSelection(2);
        }

        if (headache.getAlcohol().equals("Sí")) {
            this.alcoholSpinner.setSelection(1);
        } else {
            this.alcoholSpinner.setSelection(2);
        }

        if (headache.getSmoke().equals("Sí")) {
            this.smokeSpinner.setSelection(1);
        } else {
            this.smokeSpinner.setSelection(2);
        }

        if (headache.getMedication().equals("Sí")) {
            this.medicationSpinner.setSelection(1);
        } else {
            this.medicationSpinner.setSelection(2);
        }

        this.feelingTxt.setText(headache.getFeeling());
        this.painScaleSpinner.setSelection(headache.getPainScale());

    }

    private void saveFields() {
        startDate = startDateTxt.getText().toString();
        endDate = endDateTxt.getText().toString();
        sport = sportSpinner.getSelectedItem().toString();
        alcohol = alcoholSpinner.getSelectedItem().toString();
        smoke = smokeSpinner.getSelectedItem().toString();
        medication = medicationSpinner.getSelectedItem().toString();
        feeling = feelingTxt.getText().toString();
        painScale = painScaleSpinner.getSelectedItemPosition();
    }

}
