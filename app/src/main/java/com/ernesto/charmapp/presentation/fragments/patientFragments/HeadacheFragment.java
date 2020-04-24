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
import com.ernesto.charmapp.data.notifications.CrisisAlertReceiver;
import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.domain.retrofitEntities.Headache;
import com.ernesto.charmapp.domain.retrofitEntities.Patient;
import com.ernesto.charmapp.interactors.responses.UpdateResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.validators.HeadacheValidator;
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
                                        cancelCrisisAlarm();
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
                        createCrisisAlarm();
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
                                    createCrisisAlarm();
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
                datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis());
                final String selectedDate = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                try {
                    Date datePickerDate = new SimpleDateFormat("dd-MM-yyyy").parse(selectedDate);
                    if (!isValidDate(datePickerDate.toString())) {
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

    private boolean isValidDate(String selectedDate) {
        boolean ok = false;
        try {
            Date today = new Date();
            Date userDate = new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate);

            ok = today.before(new Date((userDate.getTime()))) && today.after(userDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean validateFields() {
        return validator.validate(startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
    }

    public void showErrorDialog(String title, String msg) {
        ErrorDialog errorDialog = new ErrorDialog(title, msg);
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }

    public void createCrisisAlarm() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 10);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), CrisisAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 2, intent, 0);
        long alarmFrequence = patient.getCh_duration();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 60 * 24 * alarmFrequence, pendingIntent);
    }


    public void cancelCrisisAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), CrisisAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 2, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

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
        System.out.println(feeling + "\n" + feelingTxt.getText().toString());
        painScale = painScaleSpinner.getSelectedItemPosition();
    }

}
