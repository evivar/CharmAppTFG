package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Diary;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.CreateDiaryResponse;
import com.ernesto.charmapp.interactors.responses.ReadDiaryResponse;
import com.ernesto.charmapp.interactors.responses.UpdateDiaryResponse;
import com.ernesto.charmapp.interactors.validators.DiaryValidator;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.skyhope.eventcalenderlibrary.model.Event;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.sql.Date;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * TODO:
 *   Leer el diario de la fecha
 *   Leer la crisis de la fecha
 *   Si existen meter los campos y activar los botones
 *   Si alguno no existe desactivar los botones
 * */
public class HistoryCrisisAndDiaryFragment extends Fragment {

    private Patient patient;
    private String date;
    private String dateString;
    private Diary diary;
    private Button expandDiaryBtn;
    private ExpandableLayout diaryExpandableLayout;
    private EditText sleepTimeTxt;
    private Spinner sportTimeSpinner;
    private Spinner alcoholDiarySpinner;
    private Spinner smokeDiarySpinner;
    private EditText feelingDiaryTxt;
    private Button saveChangesDiaryBtn;
    private DiaryValidator diaryValidator;
    private Headache headache;
    private TextView dateLbl;
    private String sleepTime;
    private String sportTime;
    private String alcohol;
    private String smoke;
    private String feeling;

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

        this.sleepTimeTxt = v.findViewById(R.id.sleepTimeTxt_history);
        this.sportTimeSpinner = v.findViewById(R.id.sportTimeSpinner_history);
        this.alcoholDiarySpinner = v.findViewById(R.id.alcoholDiarySpinner_history);
        this.smokeDiarySpinner = v.findViewById(R.id.smokeDiarySpinner_history);
        this.feelingDiaryTxt = v.findViewById(R.id.feelingDiaryTxt_history);


        // Leemos el diario y la crisis del dia seleccionado

        final Call<ReadDiaryResponse> readDiaryByDate = RetrofitClient
                .getInstance()
                .getAPI()
                .readDiaryByDate(patient.getPatientId(), dateString);
        readDiaryByDate.enqueue(new Callback<ReadDiaryResponse>() {
            @Override
            public void onResponse(Call<ReadDiaryResponse> call, Response<ReadDiaryResponse> response) {
                ReadDiaryResponse readDiaryResponse = response.body();
                if (!readDiaryResponse.getError()) {
                    if (readDiaryResponse.getDiario().getDate() != null) {
                        diary = readDiaryResponse.getDiario();
                        fillDiary();
                    } else {
                        diary = null;
                    }
                } else {
                    Toast.makeText(getActivity(), "Error con la base de datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReadDiaryResponse> call, Throwable t) {

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
                }
                else{
                    showErrorDialog("No hay un diario disponible para esta fecha");
                }
            }
        });

        saveChangesDiaryBtn = v.findViewById(R.id.editDiaryBtn_history);
        saveChangesDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                saveFields();
                diaryValidator = new DiaryValidator();
                if (diaryValidator.validate(sleepTime, sportTime, alcohol, smoke, feeling)) {
                    Call<UpdateDiaryResponse> updateDiary = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .updateDiary(patient.getPatientId(), date, sleepTime, sportTime, alcohol, smoke, feeling);
                    updateDiary.enqueue(new Callback<UpdateDiaryResponse>() {
                        @Override
                        public void onResponse(Call<UpdateDiaryResponse> call, Response<UpdateDiaryResponse> response) {
                            UpdateDiaryResponse updateDiaryResponse = response.body();
                            if (!updateDiaryResponse.getEstadoDelError()) {
                                Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                            } else if ((updateDiaryResponse.getEstadoDelError())) {
                                Toast.makeText(getActivity(), updateDiaryResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), updateDiaryResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateDiaryResponse> call, Throwable t) {

                        }
                    });
                } else {
                    showErrorDialog(diaryValidator.getWrongFields());
                }
            }
        });


        return v;
    }

    public void showErrorDialog(String msg) {
        //"Solo puedes consultar los últimos 30 días"
        ErrorDialog errorDialog = new ErrorDialog("Error", msg);
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }

    private void fillDiary() {
        sleepTimeTxt.setText(diary.getSleepTime());
        switch (diary.getSportTime()) {
            case "Nada":
                sportTimeSpinner.setSelection(1);
                break;
            case "30 Minutos":
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

    public void saveFields() {
        sleepTime = sleepTimeTxt.getText().toString();
        sportTime = sportTimeSpinner.getSelectedItem().toString();
        alcohol = alcoholDiarySpinner.getSelectedItem().toString();
        smoke = smokeDiarySpinner.getSelectedItem().toString();
        feeling = feelingDiaryTxt.getText().toString();
    }
}