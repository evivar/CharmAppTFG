package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.data.SharedPreferencesManager;
import com.ernesto.charmapp.domain.Diary;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.CreateDiaryResponse;
import com.ernesto.charmapp.interactors.validators.DiaryValidator;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryFragment extends Fragment {

    private DiaryValidator validator;

    private Patient patient;

    private Diary diary;

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

    public static DiaryFragment create(Patient patient, Diary diary){
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

        this.sleepTimeTxt = v.findViewById(R.id.sleepTimeTxt_diary);
        this.sleepTimeTxt.setText(diary.getSleepTime());
        this.sportTimeSpinner = v.findViewById(R.id.sportTimeSpinner_diary);
        this.alcoholSpinner = v.findViewById(R.id.alcoholSpinner_diary);
        this.smokeSpinner = v.findViewById(R.id.smokeSpinner_diary);
        this.feelingTxt = v.findViewById(R.id.feelingTxt_diary);
        this.feelingTxt.setText(diary.getFeeling());

        this.saveBtn = v.findViewById(R.id.saveBtn_diary);
        this.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new DiaryValidator();
                if(validator.validate(sleepTime, sportTime, alcohol, smoke, feeling)){
                    Call<CreateDiaryResponse> createDiary = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .createDiary(patient.getPatientId(), new Date(System.currentTimeMillis()).toString(), sleepTime, "123456789", sportTime, alcohol, smoke, feeling);
                    createDiary.enqueue(new Callback<CreateDiaryResponse>() {
                        @Override
                        public void onResponse(Call<CreateDiaryResponse> call, Response<CreateDiaryResponse> response) {
                            CreateDiaryResponse createDiaryResponse = response.body();
                            if(!createDiaryResponse.getEstadoDelError()){
                                Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "PATIENT_INDEX_FRAGMENT")
                                        .commit();
                            }
                            else if ((createDiaryResponse.getEstadoDelError()) && (createDiaryResponse.getMensaje().equals("Ya ha rellenado el formulario para el dia de hoy"))){
                                Toast.makeText(getActivity(), createDiaryResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getActivity(), createDiaryResponse.getMensaje(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateDiaryResponse> call, Throwable t) {

                        }
                    });
                }
                else{
                    showErrorDialog();
                }
            }
        });

        return v;
    }

    public void saveFields(){
        sleepTime = sleepTimeTxt.getText().toString();
        sportTime = sportTimeSpinner.getSelectedItem().toString();
        alcohol = alcoholSpinner.getSelectedItem().toString();
        smoke = smokeSpinner.getSelectedItem().toString();
        feeling = feelingTxt.getText().toString();
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }
}
