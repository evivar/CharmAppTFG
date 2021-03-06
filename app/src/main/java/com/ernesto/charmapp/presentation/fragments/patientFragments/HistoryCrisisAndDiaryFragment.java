package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.app.DatePickerDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.domain.retrofitEntities.Diary;
import com.ernesto.charmapp.domain.retrofitEntities.Headache;
import com.ernesto.charmapp.domain.retrofitEntities.Patient;
import com.ernesto.charmapp.interactors.responses.UpdateResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;
import com.ernesto.charmapp.interactors.validators.DiaryValidator;
import com.ernesto.charmapp.interactors.validators.HeadacheValidator;
import com.ernesto.charmapp.presentation.dialogs.DateDialog;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 *  TODO: Cuando leo una crisis que no ha terminado por fecha, no se obtiene porque end_datetime es 0000-00-00 -> Parece estar solucionado
 *
 * */
public class HistoryCrisisAndDiaryFragment extends Fragment {

    private Patient patient;
    private String date;
    private String dateString;
    private Diary diary;
    private Button expandDiaryBtn;
    private ExpandableLayout diaryExpandableLayout;
    private EditText sleepTimeDiaryTxt;
    private Spinner sportTimeDiarySpinner;
    private Spinner alcoholDiarySpinner;
    private Spinner smokeDiarySpinner;
    private EditText feelingDiaryTxt;
    private Button saveChangesDiaryBtn;
    private DiaryValidator diaryValidator;
    private TextView dateLbl;
    private String sleepTimeDiary;
    private String sportTimeDiary;
    private String alcoholDiary;
    private String smokeDiary;
    private String feelingDiary;

    private Button expandCrisisBtn;
    private ExpandableLayout crisisExpandableLayout;
    private Headache headache;
    private HeadacheValidator headacheValidator;

    private EditText startDateCrisisTxt;

    private String startDateCrisis;

    private EditText endDateCrisisTxt;

    private String endDateCrisis;

    private Spinner sportCrisisSpinner;

    private String sportCrisis;

    private Spinner alcoholCrisisSpinner;

    private String alcoholCrisis;

    private Spinner smokeCrisisSpinner;

    private String smokeCrisis;

    private Spinner medicationCrisisSpinner;

    private String medicationCrisis;

    private EditText feelingCrisisTxt;

    private String feelingCrisis;

    private Spinner painScaleCrisisSpinner;

    private int painScaleCrisis;
    private Button saveChangesCrisisBtn;

    public static HistoryCrisisAndDiaryFragment create(Patient patient, String dateString, String date) {
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        args.putSerializable("date", dateString);
        args.putSerializable("dateLbl", date);
        HistoryCrisisAndDiaryFragment f = new HistoryCrisisAndDiaryFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        patient = (Patient) arguments.getSerializable("patient");
        dateString = (String) arguments.getSerializable("date");
        date = (String) arguments.getSerializable("dateLbl");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_crisis_and_diary, container, false);

        this.dateLbl = v.findViewById(R.id.dateLbl_History);
        this.dateLbl.setText(date);

        this.sleepTimeDiaryTxt = v.findViewById(R.id.sleepTimeTxt_history);
        this.sportTimeDiarySpinner = v.findViewById(R.id.sportTimeSpinner_history);
        this.alcoholDiarySpinner = v.findViewById(R.id.alcoholDiarySpinner_history);
        this.smokeDiarySpinner = v.findViewById(R.id.smokeDiarySpinner_history);
        this.feelingDiaryTxt = v.findViewById(R.id.feelingDiaryTxt_history);


        // Leemos el diario y la crisis del dia seleccionado

        final Call<DiaryResponse> readDiaryByDate = RetrofitClient
                .getInstance()
                .getAPI()
                .readDiaryByDate(patient.getPatientId(), dateString);
        readDiaryByDate.enqueue(new Callback<DiaryResponse>() {
            @Override
            public void onResponse(Call<DiaryResponse> call, Response<DiaryResponse> response) {
                DiaryResponse diaryResponse = response.body();
                if (!diaryResponse.getError()) {
                    if (diaryResponse.getDiary().getDate() != null) {
                        diary = diaryResponse.getDiary();
                        fillDiary();
                    } else {
                        diary = null;
                    }
                }
            }

            @Override
            public void onFailure(Call<DiaryResponse> call, Throwable t) {

            }
        });

        expandDiaryBtn = v.findViewById(R.id.expandDiaryBtn_history);
        diaryExpandableLayout = v.findViewById(R.id.ExpandableLayoutDiary);
        expandDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diary != null) {
                    if (diaryExpandableLayout.isExpanded()) {
                        expandDiaryBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_expand), null);
                        diaryExpandableLayout.collapse();
                    } else {
                        expandDiaryBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_collapse), null);
                        diaryExpandableLayout.expand();
                    }
                } else {
                    showErrorDialog("No hay un diario disponible para esta fecha");
                }
            }
        });

        saveChangesDiaryBtn = v.findViewById(R.id.editDiaryBtn_history);
        saveChangesDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                saveDiaryFields();
                String diaryDate = "";
                try {
                    diaryDate = parseDiaryDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                diaryValidator = new DiaryValidator();
                if (diaryValidator.validate(sleepTimeDiary, diary.getDate(), sportTimeDiary, alcoholDiary, smokeDiary, feelingDiary, patient.getPatientId())) {

                    final Call<UpdateResponse> updateDiary = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .updateDiary(patient.getPatientId(), diaryDate, sleepTimeDiary, "123", sportTimeDiary, alcoholDiary, smokeDiary, feelingDiary);
                    updateDiary.enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                            UpdateResponse updateResponse = response.body();
                            if (!updateResponse.getError()) {
                                Toast.makeText(getActivity(), "Diario actualizado correctamente", Toast.LENGTH_LONG).show();
                                diaryExpandableLayout.setExpanded(false);
                            } else if ((updateResponse.getError())) {
                                Toast.makeText(getActivity(), updateResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), updateResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call, Throwable t) {

                        }
                    });
                } else {
                    showErrorDialog(diaryValidator.getWrongFields());
                }
            }
        });


        // pARTE DE LA CRISIS

        this.startDateCrisisTxt = v.findViewById(R.id.startDateCrisisTxt_history);
        startDateCrisisTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(startDateCrisisTxt);
            }
        });
        this.endDateCrisisTxt = v.findViewById(R.id.endDateCrisisTxt_history);
        endDateCrisisTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(endDateCrisisTxt);
            }
        });
        this.sportCrisisSpinner = v.findViewById(R.id.sportCrisisSpinner_history);
        this.alcoholCrisisSpinner = v.findViewById(R.id.alcoholCrisisSpinner_history);
        this.smokeCrisisSpinner = v.findViewById(R.id.smokeCrisisSpinner_history);
        this.medicationCrisisSpinner = v.findViewById(R.id.medicationCrisisSpinner_history);
        this.feelingCrisisTxt = v.findViewById(R.id.feelingCrisisTxt_history);
        this.painScaleCrisisSpinner = v.findViewById(R.id.painScaleCrisisSpinner_history);


        final Call<CrisisResponse> readCrisisByDateV2 = RetrofitClient
                .getInstance()
                .getAPI()
                .readCrisisByDate(patient.getPatientId(), dateString);
        readCrisisByDateV2.enqueue(new Callback<CrisisResponse>() {
            @Override
            public void onResponse(Call<CrisisResponse> call, Response<CrisisResponse> response) {
                CrisisResponse crisisResponse = response.body();
                if (!crisisResponse.getError()) {
                    if (crisisResponse.getCrisis().getStartDatetime() != null) {
                        headache = crisisResponse.getCrisis();
                        fillCrisis();
                    } else {
                        headache = null;
                    }
                }
            }

            @Override
            public void onFailure(Call<CrisisResponse> call, Throwable t) {

            }
        });

        expandCrisisBtn = v.findViewById(R.id.expandCrisisBtn_history);
        crisisExpandableLayout = v.findViewById(R.id.ExpandableLayoutCrisis);
        expandCrisisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headache != null) {
                    if (crisisExpandableLayout.isExpanded()) {
                        expandCrisisBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_expand), null);
                        crisisExpandableLayout.collapse();
                    } else {
                        expandCrisisBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_collapse), null);
                        crisisExpandableLayout.expand();
                    }
                } else {
                    showErrorDialog("No hay una crisis disponible para esta fecha");
                }
            }
        });

        saveChangesCrisisBtn = v.findViewById(R.id.editCrisisBtn_history);
        saveChangesCrisisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                saveCrisisFields();
                headacheValidator = new HeadacheValidator();
                if (headacheValidator.validate(startDateCrisis, endDateCrisis, sportCrisis, alcoholCrisis, smokeCrisis, medicationCrisis, feelingCrisis, painScaleCrisis)) {
                    final Call<UpdateResponse> updateCrisis = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .updateCrisis(patient.getPatientId(), startDateCrisis, endDateCrisis, sportCrisis, alcoholCrisis, smokeCrisis, medicationCrisis, feelingCrisis, painScaleCrisis);
                    updateCrisis.enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                            UpdateResponse updateResponse = response.body();
                            if (!updateResponse.getError()) {
                                Toast.makeText(getActivity(), "Crisis actualizada correctamente", Toast.LENGTH_LONG).show();
                                crisisExpandableLayout.setExpanded(false);
                            } else if ((updateResponse.getError())) {
                                Toast.makeText(getActivity(), updateResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), updateResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call, Throwable t) {

                        }
                    });
                } else {
                    showErrorDialog(headacheValidator.getWrongFields());
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
                    if (Calendar.getInstance().after(datePickerDate)) {
                        showErrorDialog("Error: La fecha no puede ser posterior a hoy. \n Seleccione una fecha anterior");
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

    public void showErrorDialog(String msg) {
        //"Solo puedes consultar los últimos 30 días"
        ErrorDialog errorDialog = new ErrorDialog("Error", msg);
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    private void fillDiary() {
        sleepTimeDiaryTxt.setText(diary.getSleepTime());
        Log.d("Tiempo de deporte: ", diary.getSportTime());
        switch (diary.getSportTime()) {
            case "Nada":
                sportTimeDiarySpinner.setSelection(1);
                break;
            case "30 minutos":
                sportTimeDiarySpinner.setSelection(2);
                break;
            case "1 hora":
                sportTimeDiarySpinner.setSelection(3);
                break;
            case "2 horas":
                sportTimeDiarySpinner.setSelection(4);
                break;
            case "Más de 2 horas":
                sportTimeDiarySpinner.setSelection(5);
                break;
            default:
                sportTimeDiarySpinner.setSelection(0);
                break;
        }

        switch (diary.getAlcohol()) {
            case "No":
                alcoholDiarySpinner.setSelection(1);
                break;
            case "1-2 cervezas":
                alcoholDiarySpinner.setSelection(2);
                break;
            case "Más de 2 cervezas":
                alcoholDiarySpinner.setSelection(3);
                break;
            case "1-2 copas de vino":
                alcoholDiarySpinner.setSelection(4);
                break;
            case "Más de 2 copas de vino":
                alcoholDiarySpinner.setSelection(5);
                break;
            case "1-2 copas de bebida destiladas":
                alcoholDiarySpinner.setSelection(6);
                break;
            case "Más de 2 copas de bebida destilada":
                alcoholDiarySpinner.setSelection(7);
                break;
            default:
                alcoholDiarySpinner.setSelection(0);
                break;
        }

        switch (diary.getSmoke()) {
            case "Nada":
                smokeDiarySpinner.setSelection(1);
                break;
            case "1-10 cigarrillos":
                smokeDiarySpinner.setSelection(2);
                break;
            case "10-20 cigarrillos":
                smokeDiarySpinner.setSelection(3);
                break;
            case "20-30 cigarrillos":
                smokeDiarySpinner.setSelection(4);
                break;
            case "Más de 30 cigarrillos":
                smokeDiarySpinner.setSelection(5);
                break;
            default:
                smokeDiarySpinner.setSelection(0);
                break;
        }

        feelingDiaryTxt.setText(diary.getFeeling());
    }

    private void fillCrisis() {
        if (!headache.getEndDatetime().equals(null)) {
            this.endDateCrisisTxt.setText(headache.getEndDatetime().substring(0, 10));
        } else {
            this.endDateCrisisTxt.setText("");
        }
        this.startDateCrisisTxt.setText(headache.getStartDatetime().substring(0, 10));
        if (headache.getSport().equals("Sí")) {
            this.sportCrisisSpinner.setSelection(1);
        } else {
            this.sportCrisisSpinner.setSelection(2);
        }

        if (headache.getAlcohol().equals("Sí")) {
            this.alcoholCrisisSpinner.setSelection(1);
        } else {
            this.alcoholCrisisSpinner.setSelection(2);
        }

        if (headache.getSmoke().equals("Sí")) {
            this.smokeCrisisSpinner.setSelection(1);
        } else {
            this.smokeCrisisSpinner.setSelection(2);
        }

        if (headache.getMedication().equals("Sí")) {
            this.medicationCrisisSpinner.setSelection(1);
        } else {
            this.medicationCrisisSpinner.setSelection(2);
        }

        this.feelingCrisisTxt.setText(headache.getFeeling());
        this.painScaleCrisisSpinner.setSelection(headache.getPainScale());
    }

    public void saveDiaryFields() {
        sleepTimeDiary = sleepTimeDiaryTxt.getText().toString();
        sportTimeDiary = sportTimeDiarySpinner.getSelectedItem().toString();
        alcoholDiary = alcoholDiarySpinner.getSelectedItem().toString();
        smokeDiary = smokeDiarySpinner.getSelectedItem().toString();
        feelingDiary = feelingDiaryTxt.getText().toString();
    }

    public void saveCrisisFields() {
        startDateCrisis = startDateCrisisTxt.getText().toString();
        endDateCrisis = endDateCrisisTxt.getText().toString();
        sportCrisis = sportCrisisSpinner.getSelectedItem().toString();
        alcoholCrisis = alcoholCrisisSpinner.getSelectedItem().toString();
        smokeCrisis = smokeCrisisSpinner.getSelectedItem().toString();
        medicationCrisis = medicationCrisisSpinner.getSelectedItem().toString();
        feelingCrisis = feelingCrisisTxt.getText().toString();
        painScaleCrisis = painScaleCrisisSpinner.getSelectedItemPosition();
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private String parseDiaryDate() throws ParseException {
        Date diaryDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        diaryDate = formatter.parse(this.date);
        return new SimpleDateFormat("yyyy-MM-dd").format(diaryDate);
    }
}