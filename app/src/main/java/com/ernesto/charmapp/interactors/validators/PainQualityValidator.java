package com.ernesto.charmapp.interactors.validators;

import android.widget.RadioButton;

import java.util.ArrayList;

public class PainQualityValidator {

    private String wrongFields;

    public PainQualityValidator() {
        this.wrongFields = "";
    }

    // Con el spinner hacer un switch de los id de las opciones

    public boolean validate(RadioButton location, RadioButton kind, ArrayList<String> symptomsCBoxesId) {
        boolean valid = true;

        if(location == null){
            valid = false;
            wrongFields += "Localización del dolor\n";
        }

        if(kind == null){
            valid = false;
            wrongFields += "Tipo de dolor\n";
        }

        if((symptomsCBoxesId.isEmpty()) || (symptomsCBoxesId == null)){
            valid = false;
            wrongFields += "Debe seleccionar al menos un tipo de síntoma asociado\n";
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
