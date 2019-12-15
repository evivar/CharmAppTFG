package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.os.Bundle;
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
import com.ernesto.charmapp.interactors.responses.ActiveCrisisResponse;
import com.ernesto.charmapp.interactors.responses.ReadDiaryResponse;
import com.ernesto.charmapp.interactors.responses.ReadPatientActiveCrisisResponse;
import com.ernesto.charmapp.presentation.dialogs.InfoDialog;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientIndexFragment extends Fragment {

    // Falta lo del resumen

    private Patient patient;

    private Button diaryBtn;

    private Button newAttackBtn;

    private Button historyBtn;

    private boolean activeCrisis;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_index, container, false);

        diaryBtn = v.findViewById(R.id.diarioBtn_patientMain);
        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ReadDiaryResponse> readLastDiary = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .readLastDiary(patient.getPatientId());
                readLastDiary.enqueue(new Callback<ReadDiaryResponse>() {
                    @Override
                    public void onResponse(Call<ReadDiaryResponse> call, Response<ReadDiaryResponse> response) {
                        ReadDiaryResponse readDiaryResponse = response.body();
                        if (!readDiaryResponse.getError()) {
                            Diary lastDiary = readDiaryResponse.getDiario();
                            Date date = new Date(System.currentTimeMillis());
                            // Revisar lo de si no hay ningun diario metido
                            if ((!lastDiary.getDate().equals(date.toString()))) {
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
                            Toast.makeText(getActivity(), "Error con la base de datos", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadDiaryResponse> call, Throwable t) {

                    }
                });

            }
        });

        newAttackBtn = v.findViewById(R.id.nuevaCrisisBtn_patientMain);
        newAttackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Call<ReadPatientActiveCrisisResponse> readPatientActiveCrisis = RetrofitClient.getInstance().getAPI().readPatientActiveCrisis(patient.getPatientId());
                readPatientActiveCrisis.enqueue(new Callback<ReadPatientActiveCrisisResponse>() {
                    @Override
                    public void onResponse(Call<ReadPatientActiveCrisisResponse> call, Response<ReadPatientActiveCrisisResponse> response) {
                        ReadPatientActiveCrisisResponse readPatientActiveCrisisResponse = response.body();
                        if(!readPatientActiveCrisisResponse.getError()){
                            if(readPatientActiveCrisisResponse.getCrisis().getPatientId() == null){
                                System.out.println("Nueva crisis");
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(new Headache(), patient, false), "HEADACHE_FRAGMENT")
                                        .commit();
                            }
                            else{
                                System.out.println("Editar crisis");
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                        .addToBackStack(null)
                                        .replace(R.id.fragmentContainer_patient, HeadacheFragment.create(readPatientActiveCrisisResponse.getCrisis(), patient, true), "HEADACHE_FRAGMENT")
                                        .commit();
                            }
                        }
                        else{
                            // Falla la lectura
                            Toast.makeText(getContext(), "ERROR EN LA LECTURA DEL HEADACHE", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadPatientActiveCrisisResponse> call, Throwable t) {

                    }
                });
            }
        });
        return v;
    }


}
