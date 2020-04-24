package com.ernesto.charmapp.interactors.validators;

public class HeadacheValidator {


    private String wrongFields;

    public HeadacheValidator() {
        this.wrongFields = "";
    }

    public String getWrongFields() {
        return this.wrongFields;
    }

    public void setWrongFields(String wrongFields) {
        this.wrongFields = wrongFields;
    }


    public boolean validate(String startDate, String endDate, String sport, String alcohol, String smoke, String medication, String feeling, int painScale) {
        boolean valid = true;

        if ((startDate.isEmpty()) || (startDate.length() != 10) || (startDate == null)) {
            valid = false;
            wrongFields += "Fecha de inicio\n";
        }

        if ((sport.isEmpty()) || (sport.length() != 2) || (sport == null)) {
            valid = false;
            wrongFields += "Deporte\n";
        }

        if ((alcohol.isEmpty()) || (alcohol.length() != 2) || (alcohol == null)) {
            valid = false;
            wrongFields += "Alcohol\n";
        }

        if ((smoke.isEmpty()) || (smoke.length() != 2) || (smoke == null)) {
            valid = false;
            wrongFields += "Tabaco\n";
        }

        if ((medication.isEmpty()) || (medication.length() != 2) || (medication == null)) {
            valid = false;
            wrongFields += "Medicación\n";
        }

        if ((feeling.isEmpty()) || (feeling == null)) {
            valid = false;
            wrongFields += "Sensación\n";
        }

        if ((painScale < 0) || (painScale > 10)) {
            valid = false;
            wrongFields += "Grado de dolor\n";
        }
        return valid;
    }
}
