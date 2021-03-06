package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.domain.retrofitEntities.Diary;
import com.ernesto.charmapp.domain.retrofitEntities.Patient;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;
import com.ernesto.charmapp.interactors.validators.DiaryValidator;
import com.ernesto.charmapp.presentation.dialogs.DiaryDateDialog;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TODO: Falta meter el evento en el calendario
 */
public class DiaryFragment extends Fragment {

    private DiaryValidator validator;

    private Patient patient;

    private Diary diary;

    private EditText diaryDateTxt;

    private String diaryDate;

    private EditText sleepTimeTxt;

    private String sleepTime;

    private Spinner sportTimeSpinner;

    private String sportTime;

    private Spinner alcoholSpinner;

    private String alcohol;

    private Spinner smokeSpinner;

    private String smoke;

    private EditText feelingTxt;

    private String feeling;

    private Button saveBtn;

    private CalenderEvent calenderEvent;

    public static DiaryFragment create(Patient patient, Diary diary) {
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        args.putSerializable("diary", diary);
        DiaryFragment f = new DiaryFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        patient = (Patient) arguments.getSerializable("patient");
        diary = (Diary) arguments.getSerializable("diary");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diary, container, false);

        this.diaryDateTxt = v.findViewById(R.id.diaryDateTxt_diary);
        this.diaryDateTxt.setText(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        this.sleepTimeTxt = v.findViewById(R.id.sleepTimeTxt_diary);
        this.sportTimeSpinner = v.findViewById(R.id.sportTimeSpinner_diary);
        this.alcoholSpinner = v.findViewById(R.id.alcoholSpinner_diary);
        this.smokeSpinner = v.findViewById(R.id.smokeSpinner_diary);
        this.feelingTxt = v.findViewById(R.id.feelingTxt_diary);
        this.calenderEvent = v.findViewById(R.id.calender_event);

        if (diary.getSleepTime() != (null)) {
            fillDiary();
        }

        this.diaryDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(diaryDateTxt);
            }
        });

        this.saveBtn = v.findViewById(R.id.saveBtn_diary);
        this.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                saveFields();
                validator = new DiaryValidator();
                if (validator.validate(sleepTime, diaryDate, sportTime, alcohol, smoke, feeling, patient.getPatientId())) {
                    Call<DiaryResponse> createDiary = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .createDiary(patient.getPatientId(), /*new Date(System.currentTimeMillis()).toString()*/diaryDate, sleepTime, "123456789", sportTime, alcohol, smoke, feeling);
                    createDiary.enqueue(new Callback<DiaryResponse>() {
                        @Override
                        public void onResponse(Call<DiaryResponse> call, Response<DiaryResponse> response) {
                            Log.d("Crear diario", "onResponse: " + response.body().toString());
                            DiaryResponse diaryResponse = response.body();
                            if (!diaryResponse.getError()) {
                                Calendar calendar = Calendar.getInstance();
                                Event event = new Event(calendar.getTimeInMillis(), "Diario", Color.BLUE);
                                //calenderEvent.addEvent(event);
                                Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "PATIENT_INDEX_FRAGMENT")
                                        .commit();
                            } else if ((diaryResponse.getError()) && (diaryResponse.getMensaje().equals("Ya ha rellenado el formulario para el dia de hoy"))) {
                                Toast.makeText(getActivity(), "Fecha no válida, ya has rellenado el diario para el día: " + diaryDate, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Fecha no válida, ya has rellenado el diario para el día: " + diaryDate, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DiaryResponse> call, Throwable t) {

                        }
                    });
                } else {
                    showErrorDialog();
                }
            }
        });

        return v;
    }

    public void saveFields() {
        diaryDate = diaryDateTxt.getText().toString();
        sleepTime = sleepTimeTxt.getText().toString();
        sportTime = sportTimeSpinner.getSelectedItem().toString();
        alcohol = alcoholSpinner.getSelectedItem().toString();
        smoke = smokeSpinner.getSelectedItem().toString();
        feeling = feelingTxt.getText().toString();
    }

    public void showDatePickerDialog(final EditText dateTxt) {
        DiaryDateDialog newFragment = DiaryDateDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque Enero es el 0
                final String selectedDate = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                try {
                    java.util.Date datePickerDate = new SimpleDateFormat("dd-MM-yyyy").parse(selectedDate);
                    dateTxt.setText(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }

    public void showErrorDialog(String error, String msg) {
        ErrorDialog errorDialog = new ErrorDialog(error, msg);
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }

    private boolean isValidDate(String selectedDate) {
        boolean ok = false;
        try {
            java.util.Date today = new java.util.Date();
            java.util.Date userDate = new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate);

            ok = today.before(new java.util.Date((userDate.getTime() + 16l * 60 * 60 * 24 * 1000))) && today.after(userDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ok;
    }


    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void fillDiary() {
        sleepTimeTxt.setText(diary.getSleepTime());
        switch (diary.getSportTime()) {
            case "Nada":
                sportTimeSpinner.setSelection(1);
                break;
            case "30 minutos":
                sportTimeSpinner.setSelection(2);
                break;
            case "1 hora":
                sportTimeSpinner.setSelection(3);
                break;
            case "2 horas":
                sportTimeSpinner.setSelection(4);
                break;
            case "Más de 2 horas":
                sportTimeSpinner.setSelection(5);
                break;
            default:
                sportTimeSpinner.setSelection(0);
                break;
        }

        switch (diary.getAlcohol()) {
            case "No":
                alcoholSpinner.setSelection(1);
                break;
            case "1-2 cervezas":
                alcoholSpinner.setSelection(2);
                break;
            case "Más de 2 cervezas":
                alcoholSpinner.setSelection(3);
                break;
            case "1-2 copas de vino":
                alcoholSpinner.setSelection(4);
                break;
            case "Más de 2 copas de vino":
                alcoholSpinner.setSelection(5);
                break;
            case "1-2 copas de bebida destiladas":
                alcoholSpinner.setSelection(6);
                break;
            case "Más de 2 copas de bebida destilada":
                alcoholSpinner.setSelection(7);
                break;
            default:
                alcoholSpinner.setSelection(0);
                break;
        }

        switch (diary.getSmoke()) {
            case "Nada":
                smokeSpinner.setSelection(1);
                break;
            case "1-10 cigarrillos":
                smokeSpinner.setSelection(2);
                break;
            case "10-20 cigarrillos":
                smokeSpinner.setSelection(3);
                break;
            case "20-30 cigarrillos":
                smokeSpinner.setSelection(4);
                break;
            case "Más de 30 cigarrillos":
                smokeSpinner.setSelection(5);
                break;
            default:
                smokeSpinner.setSelection(0);
                break;
        }

        feelingTxt.setText(diary.getFeeling());
    }
}
