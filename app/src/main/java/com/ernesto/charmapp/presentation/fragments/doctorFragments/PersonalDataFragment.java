package com.ernesto.charmapp.presentation.fragments.doctorFragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.RegisterForm;
import com.ernesto.charmapp.interactors.validators.PersonalDataValidator;
import com.ernesto.charmapp.presentation.activities.doctorActivities.DoctorMainActivity;
import com.ernesto.charmapp.presentation.dialogs.DateDialog;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

public class PersonalDataFragment extends Fragment {

    private RegisterForm form;

    private PersonalDataValidator validator;

    private EditText emailTxt;

    private String email;

    private EditText phoneTxt;

    private String phone;

    private EditText nameTxt;

    private String name;

    private EditText surname1Txt;

    private String surname1;

    private EditText surname2Txt;

    private String surname2;

    private EditText birthDateTxt;

    private String birthDate;

    private Spinner genderSpinner;

    private String gender;

    private Spinner professionSpinner;

    private String profession;

    private RadioGroup professionPlaceRGroup;

    private RadioButton professionPlaceRButton;

    private String professionPlace;

    private Spinner sectorSpinner;

    private String sector;

    private EditText otherSectorTxt;

    private String otherSector;

    private EditText homeCPTxt;

    private String homeCP;

    private EditText jobCPTxt;

    private String jobCP;

    public static PersonalDataFragment create(RegisterForm form) {
        Bundle args = new Bundle();
        args.putSerializable("form", form);
        PersonalDataFragment f = new PersonalDataFragment();
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
        View v = inflater.inflate(R.layout.fragment_personal_data, container, false);

        emailTxt = v.findViewById(R.id.emailTxt_personalData);
        phoneTxt = v.findViewById(R.id.phoneTxt_personalData);
        nameTxt = v.findViewById(R.id.nameTxt_personalData);
        surname1Txt = v.findViewById(R.id.surname1Txt_personalData);
        surname2Txt = v.findViewById(R.id.surname2Txt_personalData);
        birthDateTxt = v.findViewById(R.id.birdTxt_personalData);
        genderSpinner = v.findViewById(R.id.genderSpinner_personalData);
        professionSpinner = v.findViewById(R.id.professionSpinner_personalData);
        professionPlaceRGroup = v.findViewById(R.id.professionPlaceRGroup_personalData);
        sectorSpinner = v.findViewById(R.id.sectorSpinner_personalData);
        otherSectorTxt = v.findViewById(R.id.otherSectorTxt_personalData);
        homeCPTxt = v.findViewById(R.id.neighbourCPTxt_personalData);
        jobCPTxt = v.findViewById(R.id.workCPTxt_personalData);

        birthDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button nextButton = v.findViewById(R.id.nextBtn_personalData);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new PersonalDataValidator();
                // Valida los campos
                if (validateFields()) {
                    professionPlace = professionPlaceRButton.getText().toString();
                    // Rellena los datos de la clase RegisterForm
                    form.completePersonalData(email, phone, name, surname1, surname2, birthDate, gender, profession,
                            professionPlace, sector, otherSector, homeCP, jobCP);
                    //System.out.println(form);
                    // Pasa a la siguiente parte del formulario
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .addToBackStack(null)
                            .replace(R.id.fragmentContainer_doctor, GeneralQuestionsFragment.create(form), "GENERAL_QUESTIONS_FRAGMENT")
                            .commit();
                } else {
                    // Mostramos un dialogo de error con los campos a revisar y ademas los ponemos como error
                    showErrorDialog();
                }
            }
        });

        Button backButton = v.findViewById(R.id.backBtn_personalData);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DoctorMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return v;
    }

    public void showDatePickerDialog() {
        DateDialog newFragment = DateDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque Enero es el 0
                final String selectedDate = twoDigits(month + 1) + "/" + twoDigits(day) + "/" + year;
                birthDateTxt.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    public boolean validateFields() {
        return validator.validate(email, phone, name, surname1, surname2, birthDate, gender, profession,
                professionPlaceRButton, sector, otherSector, homeCP, jobCP);
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    // PRIVATE FUNCTIONS

    /**
     * This function format a int value to be printed with two digits if the value is less or equal to 9
     *
     * @param n
     * @return
     */
    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void saveFields() {
        email = emailTxt.getText().toString();
        phone = phoneTxt.getText().toString();
        name = nameTxt.getText().toString();
        surname1 = surname1Txt.getText().toString();
        surname2 = surname2Txt.getText().toString();
        birthDate = birthDateTxt.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
        profession = professionSpinner.getSelectedItem().toString();
        professionPlaceRButton = getView().findViewById(professionPlaceRGroup.getCheckedRadioButtonId());
        sector = sectorSpinner.getSelectedItem().toString();
        otherSector = otherSectorTxt.getText().toString();
        homeCP = homeCPTxt.getText().toString();
        jobCP = jobCPTxt.getText().toString();
    }

}