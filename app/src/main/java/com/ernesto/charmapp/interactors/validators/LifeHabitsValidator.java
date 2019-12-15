package com.ernesto.charmapp.interactors.validators;

import android.widget.RadioButton;

public class LifeHabitsValidator {

    private String wrongFields;

    public LifeHabitsValidator() {
        this.wrongFields = "";
    }

    // Con el spinner hacer un switch de los id de las opciones

    public boolean validate(RadioButton smoking, RadioButton alcohol, RadioButton caffeine, RadioButton diet, RadioButton sport) {
        boolean valid = true;

        if (smoking == null) {
            valid = false;
            wrongFields += "Fumador\n";
        }

        if (alcohol == null) {
            valid = false;
            wrongFields += "Alcohol\n";
        }

        if (caffeine == null) {
            valid = false;
            wrongFields += "Cafeina\n";
        }

        if (diet == null) {
            valid = false;
            wrongFields += "Dieta\n";
        }

        if (sport == null) {
            valid = false;
            wrongFields += "Deporte\n";
        }
        return valid;
    }

    public String getWrongFields() {
        return this.wrongFields;
    }

    public void setWrongFields(String wrongFields) {
        this.wrongFields = wrongFields;
    }
}
