package com.ernesto.charmapp.presentation.fragments.doctorFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.RegisterForm;
import com.ernesto.charmapp.interactors.validators.GeneralQuestionsValidator;
import com.ernesto.charmapp.presentation.dialogs.DateDialog;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import java.util.ArrayList;

public class GeneralQuestionsFragment extends Fragment {

    private RegisterForm form;

    private GeneralQuestionsValidator validator;

    private EditText startingSymptomsTxt;

    private String startingSymptoms;

    private EditText diagnosisDateTxt;

    private String diagnosisDate;

    private RadioGroup familiarRGroup;

    private RadioButton familiarRButton;

    private String familiar;

    private EditText annualHeadachesTxt;

    private String annualHeadaches;

    private EditText outbreakDurationTxt;

    private String outbreakDuration;

    private LinearLayout seasonsLayout;

    private ArrayList<Integer> seasonsCBoxesId;

    private ArrayList<String> seasons;

    private EditText seasonsOtherTxt;

    private String seasonsOther;

    private LinearLayout causesLayout;

    private ArrayList<Integer> causesCBoxesId;

    private ArrayList<String> causes;

    private EditText causesOtherTxt;

    private String causesOther;

    private RadioGroup otherHeadacheRGroup;

    private RadioButton otherHeadacheRButton;

    private EditText otherHeadacheOtherTxt;

    private String otherHeadache;

    private RadioGroup chronicIllnessRGroup;

    private RadioButton chronicIllnessRButton;

    private EditText chronicIllnessOtherTxt;

    private String chronicIllness;

    private LinearLayout otherIllnessMedicationLayout;

    private ArrayList<Integer> otherIllnessMedicationCBoxesId;

    private ArrayList<String> otherIllnessMedication;

    private EditText otherIllnessMedicationOtherTxt;

    private String otherIllnessMedicationOther;

    private LinearLayout provenTreatmentsLayout;

    private ArrayList<Integer> provenTreatmentsCBoxesId;

    private ArrayList<String> provenTreatments;

    private EditText provenTreatmentsOtherTxt;

    private String provenTreatmentsOther;

    private LinearLayout provingTreatmentsLayout;

    private ArrayList<Integer> provingTreatmentsCBoxesId;

    private ArrayList<String> provingTreatments;

    private EditText provingTreatmentsOtherTxt;

    private String provingTreatmentsOther;

    private LinearLayout acutePhaseTreatmentsLayout;

    private ArrayList<Integer> acutePhaseTreatmentsCBoxesId;

    private ArrayList<String> acutePhaseTreatments;

    private EditText actutePhaseTreatmentsOtherTxt;

    private String acutePhaseTreatmentsOther;

    private RadioGroup tracingHospitalRGroup;

    private RadioButton tracingHospitalRButton;

    private EditText tracingHospitalOtherTxt;

    private String tracingHospital;


    public static GeneralQuestionsFragment create(RegisterForm form) {
        Bundle args = new Bundle();
        args.putSerializable("form", form);
        GeneralQuestionsFragment f = new GeneralQuestionsFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        form = (RegisterForm) arguments.getSerializable("form");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_general_questions, container, false);

        startingSymptomsTxt = v.findViewById(R.id.startingSymptomsTxt_generalQuestions);
        diagnosisDateTxt = v.findViewById(R.id.diagnosticationDateTxt_generalQuestions);
        familiarRGroup = v.findViewById(R.id.diagnosticatedFamiliarRGroup_generalQuestions);
        annualHeadachesTxt = v.findViewById(R.id.annualHeadachesTxt_generalQuestions);
        outbreakDurationTxt = v.findViewById(R.id.headachesDurationTxt_generalQuestions);
        seasonsLayout = v.findViewById(R.id.seasonalOutbreaksCBoxes_generalQuestions);
        seasonsCBoxesId = new ArrayList<>();
        for (int i = 0; i < seasonsLayout.getChildCount(); i++) {
            seasonsCBoxesId.add(seasonsLayout.getChildAt(i).getId());
        }
        seasons = new ArrayList<>();
        causesLayout = v.findViewById(R.id.causesCBoxes_generalQuestions);
        causesCBoxesId = new ArrayList<>();
        for (int i = 0; i < causesLayout.getChildCount(); i++) {
            causesCBoxesId.add(causesLayout.getChildAt(i).getId());
        }
        causes = new ArrayList<>();
        causesOtherTxt = v.findViewById(R.id.causesOtherTxt_generalQuestions);
        otherHeadacheRGroup = v.findViewById(R.id.otherHeadacheRGroup_generalQuestions);
        otherHeadacheOtherTxt = v.findViewById(R.id.otherHeadacheOtherTxt_generalQuestions);
        chronicIllnessRGroup = v.findViewById(R.id.chronicIllnessRGroup_generalQuestions);
        chronicIllnessOtherTxt = v.findViewById(R.id.otherChronicIllnessOtherTxt_generalQuestions);
        otherIllnessMedicationLayout = v.findViewById(R.id.otherIllnessMedicationCBoxes_generalQuestions);
        otherIllnessMedicationCBoxesId = new ArrayList<>();
        for (int i = 0; i < otherIllnessMedicationLayout.getChildCount(); i++) {
            otherIllnessMedicationCBoxesId.add(otherIllnessMedicationLayout.getChildAt(i).getId());
        }
        otherIllnessMedication = new ArrayList<>();
        otherIllnessMedicationOtherTxt = v.findViewById(R.id.otherIllnessMedicationOtherTxt_generalQuestions);
        provenTreatmentsLayout = v.findViewById(R.id.provenTreatmentsCBoxes_generalQuestions);
        provenTreatmentsCBoxesId = new ArrayList<>();
        for (int i = 0; i < provenTreatmentsLayout.getChildCount(); i++) {
            provenTreatmentsCBoxesId.add(provenTreatmentsLayout.getChildAt(i).getId());
        }
        provenTreatments = new ArrayList<>();
        provenTreatmentsOtherTxt = v.findViewById(R.id.provenTreatmentsOtherTxt_generalQuestions);
        provingTreatmentsLayout = v.findViewById(R.id.provingTreatmentsCBoxes_generalQuestions);
        provingTreatmentsCBoxesId = new ArrayList<>();
        for (int i = 0; i < provingTreatmentsLayout.getChildCount(); i++) {
            provingTreatmentsCBoxesId.add(provingTreatmentsLayout.getChildAt(i).getId());
        }
        provingTreatments = new ArrayList<>();
        provingTreatmentsOtherTxt = v.findViewById(R.id.provingTreatmentsOtherTxt_generalQuestions);
        acutePhaseTreatmentsLayout = v.findViewById(R.id.acutePhaseTreatmentCBoxes_generalQuestions);
        acutePhaseTreatmentsCBoxesId = new ArrayList<>();
        for (int i = 0; i < acutePhaseTreatmentsLayout.getChildCount(); i++) {
            acutePhaseTreatmentsCBoxesId.add(acutePhaseTreatmentsLayout.getChildAt(i).getId());
        }
        acutePhaseTreatments = new ArrayList<>();
        actutePhaseTreatmentsOtherTxt = v.findViewById(R.id.acutePhaseTreatmentOtherTxt_generalQuestions);
        tracingHospitalRGroup = v.findViewById(R.id.tracingHospitalRGroup_generalQuestions);
        tracingHospitalOtherTxt = v.findViewById(R.id.tracingHospitalOtherTxt_generalQuestions);

        startingSymptomsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(startingSymptomsTxt);
            }
        });

        diagnosisDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(diagnosisDateTxt);
            }
        });

        Button nextButton = v.findViewById(R.id.nextBtn_generalQuestions);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new GeneralQuestionsValidator();
                // Valida los campos
                if (validateFields()) {
                    // Guardamos los RButton
                    familiar = familiarRButton.getTag().toString();
                    if ((otherHeadache.isEmpty()) || (otherHeadache == null)) {
                        otherHeadache = otherHeadacheRButton.getTag().toString();
                    }

                    if ((chronicIllness.isEmpty()) || (chronicIllness == null)) {
                        chronicIllness = chronicIllnessRButton.getTag().toString();
                    }

                    if ((tracingHospital.isEmpty()) || (tracingHospital == null)) {
                        tracingHospital = tracingHospitalRButton.getTag().toString();
                    }
                    // Rellena los datos de la clase RegisterForm
                    /*form.completePersonalData(email, phone, name, surname1, surname2, patient_id, birthDate, gender, profession,
                            professionPlace, sector, otherSector, homeCP, jobCP);*/
                    //System.out.println(form);
                    // Pasa a la siguiente parte del formulario
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .addToBackStack(null)
                            .replace(R.id.fragmentContainer_doctor, LifeHabitsFragment.create(form), "LIFE_HABITS_FRAGMENT")
                            .commit();
                } else {
                    // Mostramos un dialogo de error con los campos a revisar y ademas los ponemos como error
                    showErrorDialog();
                }
            }
        });

        Button backButton = v.findViewById(R.id.backBtn_generalQuestions);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_register, PersonalDataFragment.create(form), "PERSONAL_DATA_FRAGMENT")
                        .commit();
            }
        });

        return v;
    }



    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    public boolean validateFields() {
        return validator.validate(startingSymptoms, diagnosisDate, familiarRButton, annualHeadaches, outbreakDuration, seasons, causes, causesOther,
                otherHeadacheRButton, otherHeadache, chronicIllnessRButton, chronicIllness, otherIllnessMedication, otherIllnessMedicationOther,
                provenTreatments, provenTreatmentsOther, provingTreatments, provingTreatmentsOther, acutePhaseTreatments, acutePhaseTreatmentsOther, tracingHospitalRButton, tracingHospital);
    }


    public void showDatePickerDialog(final EditText text) {
        DateDialog newFragment = DateDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque Enero es el 0
                final String selectedDate = twoDigits(month + 1) + "/" + twoDigits(day) + "/" + year;
                text.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    // PRIVATE FUNCTIONS

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void saveFields() {
        startingSymptoms = startingSymptomsTxt.getText().toString();
        diagnosisDate = diagnosisDateTxt.getText().toString();
        annualHeadaches = annualHeadachesTxt.getText().toString();
        outbreakDuration = outbreakDurationTxt.getText().toString();

        familiarRButton = getView().findViewById(familiarRGroup.getCheckedRadioButtonId());
        otherHeadacheRButton = getView().findViewById(otherHeadacheRGroup.getCheckedRadioButtonId());
        if ((otherHeadacheRButton != null) && (otherHeadacheRButton.getTag().toString().equals("Otros"))) {
            otherHeadache = otherHeadacheOtherTxt.getText().toString().toUpperCase();
        }

        chronicIllnessRButton = getView().findViewById(chronicIllnessRGroup.getCheckedRadioButtonId());
        if ((chronicIllnessRButton != null) && (chronicIllnessRButton.getTag().toString().equals("Otros"))) {
            chronicIllness = chronicIllnessOtherTxt.getText().toString().toUpperCase();
        }

        tracingHospitalRButton = getView().findViewById(tracingHospitalRGroup.getCheckedRadioButtonId());
        if ((tracingHospitalRButton != null) && (tracingHospitalRButton.getTag().toString().equals("Otros"))) {
            tracingHospital = tracingHospitalOtherTxt.getText().toString().toUpperCase();
        }

        for (Integer id : seasonsCBoxesId) {
            CheckBox cb = seasonsLayout.findViewById(id);
            if (cb.isChecked()) {
                seasons.add(seasonsLayout.findViewById(id).getTag().toString());
            }
        }

        for (Integer id : causesCBoxesId) {
            CheckBox cb = causesLayout.findViewById(id);
            if (cb.isChecked()) {
                causes.add(causesLayout.findViewById(id).getTag().toString());
            }
        }
        if ((causes != null) && (causes.contains("Otros"))) {
            causesOther = causesOtherTxt.getText().toString();
        }

        for (Integer id : otherIllnessMedicationCBoxesId) {
            CheckBox cb = otherIllnessMedicationLayout.findViewById(id);
            if (cb.isChecked()) {
                otherIllnessMedication.add(otherIllnessMedicationLayout.findViewById(id).getTag().toString());
            }
        }
        if ((otherIllnessMedication != null) && (otherIllnessMedication.contains("Otros"))) {
            otherIllnessMedicationOther = otherIllnessMedicationOtherTxt.getText().toString().toUpperCase();
        }

        for (Integer id : provenTreatmentsCBoxesId) {
            CheckBox cb = provenTreatmentsLayout.findViewById(id);
            if (cb.isChecked()) {
                provenTreatments.add(provenTreatmentsLayout.findViewById(id).getTag().toString());
            }
        }
        if ((provingTreatments != null) && (provenTreatments.contains("Otros"))) {
            provenTreatmentsOther = provenTreatmentsOtherTxt.getText().toString().toUpperCase();
        }

        for (Integer id : provingTreatmentsCBoxesId) {
            CheckBox cb = provingTreatmentsLayout.findViewById(id);
            if (cb.isChecked()) {
                provingTreatments.add(provingTreatmentsLayout.findViewById(id).getTag().toString());
            }
        }
        if ((provingTreatments != null) && (provingTreatments.contains("Otros"))) {
            provingTreatmentsOther = provingTreatmentsOtherTxt.getText().toString().toUpperCase();
        }

        for (Integer id : acutePhaseTreatmentsCBoxesId) {
            CheckBox cb = acutePhaseTreatmentsLayout.findViewById(id);
            if (cb.isChecked()) {
                acutePhaseTreatments.add(acutePhaseTreatmentsLayout.findViewById(id).getTag().toString());
            }
        }
        if ((acutePhaseTreatments != null) && (acutePhaseTreatments.contains("Otros"))) {
            acutePhaseTreatmentsOther = actutePhaseTreatmentsOtherTxt.getText().toString().toUpperCase();
        }
    }

}
