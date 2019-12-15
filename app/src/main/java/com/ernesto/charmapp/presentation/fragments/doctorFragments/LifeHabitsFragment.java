package com.ernesto.charmapp.presentation.fragments.doctorFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.RegisterForm;
import com.ernesto.charmapp.interactors.validators.LifeHabitsValidator;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

public class LifeHabitsFragment extends Fragment {

    private RegisterForm form;

    private LifeHabitsValidator validator;

    private RadioGroup smokingRGroup;

    private RadioButton smokingRButton;

    private String smoking;

    private RadioGroup alcoholRGroup;

    private RadioButton alcoholRButton;

    private String alcohol;

    private RadioGroup caffeineRGroup;

    private RadioButton caffeineRButton;

    private String caffeine;

    private RadioGroup dietRGroup;

    private RadioButton dietRButton;

    private String diet;

    private RadioGroup sportRGroup;

    private RadioButton sportRButton;

    private String sport;

    public static LifeHabitsFragment create(RegisterForm form) {
        Bundle args = new Bundle();
        args.putSerializable("form", form);
        LifeHabitsFragment f = new LifeHabitsFragment();
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
        View v = inflater.inflate(R.layout.fragment_life_habits, container, false);

        smokingRGroup = v.findViewById(R.id.smokingRGroup_lifeHabits);
        alcoholRGroup = v.findViewById(R.id.alcoholRGroup_lifeHabits);
        caffeineRGroup = v.findViewById(R.id.caffeineRGroup_lifeHabits);
        dietRGroup = v.findViewById(R.id.dietRGroup_lifeHabits);
        sportRGroup = v.findViewById(R.id.sportRGroup_lifeHabits);


        Button nextButton = v.findViewById(R.id.nextBtn_lifeHabits);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFields();
                validator = new LifeHabitsValidator();
                // Valida los campos
                if (validateFields()) {
                    smoking = smokingRButton.getTag().toString();
                    alcohol = alcoholRButton.getTag().toString();
                    caffeine = caffeineRButton.getTag().toString();
                    diet = dietRButton.getTag().toString();
                    sport = sportRButton.getTag().toString();
                    // Rellena los datos de la clase RegisterForm
                    form.completeLifeHabits(smoking, alcohol, caffeine, diet, sport);
                    // Pasa a la siguiente parte del formulario
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .addToBackStack(null)
                            .replace(R.id.fragmentContainer_doctor, PainQualityFragment.create(form), "PAIN_QUALITY_FRAGMENT")
                            .commit();
                } else {
                    // Mostramos un dialogo de error con los campos a revisar y ademas los ponemos como error
                    showErrorDialog();
                }
            }
        });

        Button backButton = v.findViewById(R.id.backBtn_lifeHabits);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_doctor, GeneralQuestionsFragment.create(form), "GENERAL_QUESTIONS_FRAGMENT")
                        .commit();
            }
        });

        return v;
    }


    public boolean validateFields() {
        return validator.validate(smokingRButton, alcoholRButton, caffeineRButton, dietRButton, sportRButton);
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error: Revise los siguientes campos antes de continuar", validator.getWrongFields());
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    // PRIVATE FUNCTIONS


    private void saveFields() {
        smokingRButton = getView().findViewById(smokingRGroup.getCheckedRadioButtonId());
        alcoholRButton = getView().findViewById(alcoholRGroup.getCheckedRadioButtonId());
        caffeineRButton = getView().findViewById(caffeineRGroup.getCheckedRadioButtonId());
        dietRButton = getView().findViewById(dietRGroup.getCheckedRadioButtonId());
        sportRButton = getView().findViewById(sportRGroup.getCheckedRadioButtonId());
    }

}
