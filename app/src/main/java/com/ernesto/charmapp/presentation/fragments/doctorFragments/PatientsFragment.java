package com.ernesto.charmapp.presentation.fragments.doctorFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Doctor;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.adaptors.PatientListAdapter;
import com.ernesto.charmapp.interactors.responses.ChangePasswordResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllPatientsResponse;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.ernesto.charmapp.presentation.fragments.patientFragments.PatientProfileFragment;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatientsFragment extends Fragment {

    private Doctor doctor; // Solo por si acaso se necesita algun dato del docotor o lo que sea

    private RecyclerView reciclerView;

    private PatientListAdapter patientListAdapter;

    private List<Patient> patientList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patients, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reciclerView = view.findViewById(R.id.recyclerView);
        reciclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<ReadAllPatientsResponse> readAllPatients = RetrofitClient.getInstance().getAPI().readAllPatients();
        readAllPatients.enqueue(new Callback<ReadAllPatientsResponse>() {
            @Override
            public void onResponse(Call<ReadAllPatientsResponse> call, Response<ReadAllPatientsResponse> response) {

                if(!response.body().getError()) {
                    patientList = response.body().getPacientes();
                    patientListAdapter = new PatientListAdapter(getActivity(), patientList);
                    reciclerView.setAdapter(patientListAdapter);
                }
                else{
                    Toast.makeText(getActivity(), "ERROR: No hay pacientes que mostrar", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ReadAllPatientsResponse> call, Throwable t) {

            }
        });

    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", "Contraseña actual");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


}
