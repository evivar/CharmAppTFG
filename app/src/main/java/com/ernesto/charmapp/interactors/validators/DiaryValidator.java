package com.ernesto.charmapp.interactors.validators;

import android.util.Patterns;

public class DiaryValidator {

    private String wrongFields;

    public DiaryValidator() {
        this.wrongFields = "";
    }


    // Recorrer el objeto fields con un foreach
    // Dentro del bucle distinguir entre si es un string
    public boolean validate(String sleep_time, String sport_time, String alcohol, String smoke, String feeling) {
        boolean valid = true;

        if((sleep_time == null) || (sleep_time.isEmpty()) || (sleep_time.length() == 0) || (sleep_time.length() > 3)){
            valid = false;
            wrongFields += "Tiempo de sueño\n";
        }

        if((sport_time == null) || (sport_time.equals("")) || (sport_time.length() == 0)){
            valid = false;
            wrongFields += "Tiempo de ejercicio\n";
        }

        if((alcohol == null) || (alcohol.equals("")) || (alcohol.length() == 0)){
            valid = false;
            wrongFields += "Alcohol\n";
        }

        if((smoke == null) || (smoke.equals("")) || (smoke.length() == 0)){
            valid = false;
            wrongFields += "Cigarrillos fumados\n";
        }

        if((feeling == null) || (feeling.isEmpty()) || (feeling.length() == 0) || (feeling.length() > 255)){
            valid = false;
            wrongFields += "Sensaciones\n";
        }

        return valid;
    }

    public String getWrongFields(){
        return this.wrongFields;
    }
}
