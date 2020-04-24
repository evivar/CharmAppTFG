package com.ernesto.charmapp.presentation.fragments.doctorFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.retrofitEntities.RegisterForm;
import com.ernesto.charmapp.interactors.validators.PainQualityValidator;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import java.util.ArrayList;

public class PainQualityFragment extends Fragment {

    private RegisterForm form;

    private PainQualityValidator validator;

    private RadioGroup locationRGroup;

    private RadioButton locationRButton;

    private String location;

    private RadioGroup kindRGroup;

    private RadioButton kindRButton;

    private String kind;

    private LinearLayout symptomsLayout;

    private ArrayList<Integer> symptomsCBoxesId;

    private ArrayList<String> symptoms;

    // Faltan los checkBoxes

    public static PainQualityFragment create(RegisterForm form) {
        Bundle args = new Bundle();
        args.putSerializable("form", form);
        PainQualityFragment f = new PainQualityFragment();
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
        View v = inflater.inflate(R.layout.fragment_pain_quality, container, false);

        locationRGroup = v.findViewById(R.id.locationRGroup_painQuality);
        kindRGroup = v.findViewById(R.id.kindRGroup_painQuality);
        symptomsLayout = v.findViewById(R.id.symptomsCBoxes_painQuality);
        symptomsCBoxesId = new ArrayList<>();
        for (int i = 0; i < symptomsLayout.getChildCount(); i++) {
            symptomsCBoxesId.add(symptomsLayout.getChildAt(i).getId());
        }
        Button nextButton = v.findViewById(R.id.nextBtn_painQuality);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new PainQualityValidator();
                // Valida los campos
                if (validateFields()) {
                    location = locationRButton.getTag().toString();
                    kind = kindRButton.getTag().toString();
                    // Rellena los datos de la clase RegisterForm
                    form.completePainQuality(location, kind, symptoms);
                    // Registra al paciente en la base de datos y si la respuesta es correcta nos vamos al login
                } else {
                    // Mostramos un dialogo de error con los campos a revisar y ademas los ponemos como error
                    showErrorDialog();
                }
            }
        });

        Button backButton = v.findViewById(R.id.backBtn_painQuality);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_doctor, LifeHabitsFragment.create(form), "LIFE_HABITS_FRAGMENT")
                        .commit();
            }
        });

        return v;
    }


    public boolean validateFields() {
        return validator.validate(locationRButton, kindRButton, symptoms);
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    // PRIVATE FUNCTIONS


    private void saveFields() {
        locationRButton = getView().findViewById(locationRGroup.getCheckedRadioButtonId());
        kindRButton = getView().findViewById(kindRGroup.getCheckedRadioButtonId());
        symptoms = new ArrayList<>();
        for (Integer id : symptomsCBoxesId) {
            CheckBox cb = symptomsLayout.findViewById(id);
            if (cb.isChecked()) {
                symptoms.add(symptomsLayout.findViewById(id).getTag().toString());
            }
        }
    }

}
