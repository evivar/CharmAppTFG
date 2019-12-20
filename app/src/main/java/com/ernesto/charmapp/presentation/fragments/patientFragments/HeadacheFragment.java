package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.CreateCrisisResponse;
import com.ernesto.charmapp.interactors.responses.CreateDiaryResponse;
import com.ernesto.charmapp.interactors.responses.UpdateCrisisResponse;
import com.ernesto.charmapp.interactors.validators.HeadacheValidator;
import com.ernesto.charmapp.interactors.validators.PersonalDataValidator;
import com.ernesto.charmapp.presentation.dialogs.DateDialog;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.ernesto.charmapp.presentation.fragments.doctorFragments.GeneralQuestionsFragment;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeadacheFragment extends Fragment {

    private Headache headache;

    private Patient patient;

    private boolean editing;

    private HeadacheValidator validator;

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
        startDateTxt = v.findViewById(R.id.startDateTxt_headache);
        endDateTxt = v.findViewById(R.id.endDateTxt_headache);
        sportSpinner = v.findViewById(R.id.sportSpinner_headache);
        alcoholSpinner = v.findViewById(R.id.alcoholSpinner_headache);
        smokeSpinner = v.findViewById(R.id.smokeSpinner_headache);
        medicationSpinner = v.findViewById(R.id.medicationSpinner_headache);
        feelingTxt = v.findViewById(R.id.feelingTxt_headache);
        painScaleSpinner = v.findViewById(R.id.painScaleSpinner_headache);
        saveBtn = v.findViewById(R.id.saveBtn_headache);

        if(editing){
            fillFields();
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
        // FALLA ALGO CUANDO LA CRISIS ES NUEVA, SE GUARDA EN LA BBDD PERO NO REDIRIGE AL FRAGMENT ANTERIOR
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new HeadacheValidator();
                // Valida los campos
                if (validateFields()) {
                    if ((endDate.isEmpty()) || (endDate.length() != 10) || (endDate == null)){
                        endDate = "0000-00-00";
                    }
                    // Llama al api para meter el headache
                    if(editing){
                        // Solo se updatea
                        Call<UpdateCrisisResponse> updateCrisis = RetrofitClient
                                .getInstance()
                                .getAPI()
                                .updateCrisis(patient.getPatientId(), startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
                        updateCrisis.enqueue(new Callback<UpdateCrisisResponse>() {
                            @Override
                            public void onResponse(Call<UpdateCrisisResponse> call, Response<UpdateCrisisResponse> response) {
                                UpdateCrisisResponse updateCrisisResponse = response.body();
                                if(!updateCrisisResponse.getEstadoDelError()){
                                    Toast.makeText(getActivity(), "Crisis actualizada correctamente", Toast.LENGTH_LONG).show();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                            .addToBackStack(null)
                                            .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "PATIENT_INDEX_FRAGMENT")
                                            .commit();
                                }
                                else{
                                    Toast.makeText(getActivity(), updateCrisisResponse.getMensaje(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateCrisisResponse> call, Throwable t) {

                            }
                        });
                    }
                    if(!editing){
                        Call<CreateCrisisResponse> createCrisis = RetrofitClient
                                .getInstance()
                                .getAPI()
                                .createCrisis(patient.getPatientId(), "12345678", startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
                        createCrisis.enqueue(new Callback<CreateCrisisResponse>() {
                            @Override
                            public void onResponse(Call<CreateCrisisResponse> call, Response<CreateCrisisResponse> response) {
                                CreateCrisisResponse createCrisisResponse = response.body();
                                if(createCrisisResponse != null && !createCrisisResponse.getEstadoDelError()){
                                    System.out.println("Crisis creada correctamente");
                                    Toast.makeText(getActivity(), "Crisis creada correctamente", Toast.LENGTH_LONG).show();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                                            .replace(R.id.fragmentContainer_patient, PatientIndexFragment.create(patient), "PATIENT_INDEX_FRAGMENT")
                                            .addToBackStack(null)
                                            .commit();
                                }
                                else{
                                    Toast.makeText(getActivity(), "ERROR: No se pudo crear la crisis", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateCrisisResponse> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    // Mostramos un dialogo de error con los campos a revisar y ademas los ponemos como error
                    showErrorDialog();
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
                dateTxt.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public boolean validateFields() {
        return validator.validate(startDate, endDate, sport, alcohol, smoke, medication, feeling, painScale);
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void fillFields(){
        this.startDateTxt.setText(headache.getStartDatetime().substring(0, 10));
        if(headache.getSport().equals("Sí")){
            this.sportSpinner.setSelection(1);
        }
        else{
            this.sportSpinner.setSelection(2);
        }

        if(headache.getAlcohol().equals("Sí")){
            this.alcoholSpinner.setSelection(1);
        }
        else{
            this.alcoholSpinner.setSelection(2);
        }

        if(headache.getSmoke().equals("Sí")){
            this.smokeSpinner.setSelection(1);
        }
        else{
            this.smokeSpinner.setSelection(2);
        }

        if(headache.getMedication().equals("Sí")){
            this.medicationSpinner.setSelection(1);
        }
        else{
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
