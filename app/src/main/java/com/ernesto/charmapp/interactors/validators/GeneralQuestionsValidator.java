package com.ernesto.charmapp.interactors.validators;

import android.widget.RadioButton;

import java.util.ArrayList;

public class GeneralQuestionsValidator {

    private String wrongFields;

    public GeneralQuestionsValidator() {
        this.wrongFields = "";
    }

    public String getWrongFields() {
        return this.wrongFields;
    }

    public void setWrongFields(String wrongFields) {
        this.wrongFields = wrongFields;
    }

    public boolean validate(String startingSymptoms, String diagnosisDate, RadioButton familiarRButton, String annualHeadaches, String outbreakDuration, ArrayList<String> seasons, ArrayList<String> causes,
                            String causesOther, RadioButton otherHeadacheRButton, String otherHeadache, RadioButton chronicIllnessRButton, String chronicIllness, ArrayList<String> otherIllnessMedication,
                            String otherIllnessMedicationOther, ArrayList<String> provenTreatments, String provenTreatmentsOther, ArrayList<String> provingTreatments, String provingTreatmentsOther,
                            ArrayList<String> acutePhaseTreatments, String acutePhaseTreatmentsOther, RadioButton tracingHospitalRButton, String tracingHospital) {

        boolean valid = true;

        if ((startingSymptoms.isEmpty()) || (startingSymptoms.length() == 0) || (startingSymptoms == null)) {
            valid = false;
            wrongFields += "Fecha de inicio de las cefaleas\n";
        }

        if ((diagnosisDate.isEmpty()) || (diagnosisDate.length() != 10) || (diagnosisDate == null)) {
            valid = false;
            wrongFields += "Fecha en la que fue diagnosticado\n";
        }

        if (familiarRButton == null) {
            valid = false;
            wrongFields += "Familiares diagnosticados\n";
        }

        if ((annualHeadaches.isEmpty()) || (annualHeadaches.length() == 0) || (Integer.parseInt(annualHeadaches) == 0) || (annualHeadaches == null)) {
            valid = false;
            wrongFields += "Brotes anuales\n";
        }

        if ((outbreakDuration.isEmpty()) || (outbreakDuration.length() == 0) || (outbreakDuration == null)) {
            valid = false;
            wrongFields += "Duración de los brotes\n";
        }

        if (seasons == null) {
            valid = false;
            wrongFields += "Estaciónes en las que los brotes son más frecuentes\n";
        }

        if ((causes == null) || (causes.isEmpty())) {
            valid = false;
            wrongFields += "Causas de los brotes\n";
        }
        else if ((causes.contains("Otros")) && ((causesOther.isEmpty()) || (causesOther.length() == 0) || (causesOther == null))) {
            valid = false;
            wrongFields += "Indique que otros desencadenantes de dolor reconoce\n";
        }

        if (otherHeadacheRButton == null) {
            valid = false;
            wrongFields += "Cefaleas asociadas\n";
        }
        else if ((otherHeadacheRButton.getTag().toString().equals("Otros")) && ((otherHeadache.isEmpty()) || (otherHeadache.length() == 0) || (otherHeadache == null))) {
            valid = false;
            wrongFields += "Indique que otras cefaleas asociadas tiene\n";
        }

        if (chronicIllnessRButton == null) {
            valid = false;
            wrongFields += "Enfermedades crónicas diagnosticadas\n";
        }
        else if ((chronicIllnessRButton.getTag().toString().equals("Otros")) && ((chronicIllness.isEmpty()) || (chronicIllness.length() == 0) || (chronicIllness == null))) {
            valid = false;
            wrongFields += "Indique que enfremedades cronicas tiene diagnosticadas\n";
        }

        if ((otherIllnessMedication == null) || (otherIllnessMedication.isEmpty())) {
            valid = false;
            wrongFields += "Medicación que toma para otras enfermedades\n";
        }
        else if ((otherIllnessMedication.contains("Otros")) && ((otherIllnessMedicationOther.isEmpty()) || (otherIllnessMedicationOther.length() == 0) || (otherIllnessMedicationOther == null))) {
            valid = false;
            wrongFields += "Indique que otras medicaciones toma para otras enfermedades\n";
        }

        if ((provenTreatments == null) || (provenTreatments.isEmpty())) {
            valid = false;
            wrongFields += "Tratamientos que ha probado hasta la fecha\n";
        }
        else if ((provenTreatments.contains("Otros")) && ((provenTreatmentsOther.isEmpty()) || (provenTreatmentsOther.length() == 0) || (provenTreatmentsOther == null))) {
            valid = false;
            wrongFields += "Indique que otros tratamientos ha probado hasta la fecha\n";
        }

        if ((provingTreatments == null) || (provingTreatments.isEmpty())) {
            valid = false;
            wrongFields += "Tratamientos que esta tomando actualmente\n";
        }
        else if ((provingTreatments.contains("Otros")) && ((provingTreatmentsOther.isEmpty()) || (provingTreatmentsOther.length() == 0) || (provingTreatmentsOther == null))) {
            valid = false;
            wrongFields += "Indique que otros tratamientos esta tomando actualmente\n";
        }

        if ((acutePhaseTreatments == null) || (acutePhaseTreatments.isEmpty())) {
            valid = false;
            wrongFields += "Tratamientos de fase aguda\n";
        }
        else if ((acutePhaseTreatments.contains("Otros")) && ((acutePhaseTreatmentsOther.isEmpty()) || (acutePhaseTreatmentsOther.length() == 0) || (acutePhaseTreatmentsOther == null))) {
            valid = false;
            wrongFields += "Indique que otros tratamientos de fase aguda suele tomar\n";
        }

        if (tracingHospitalRButton == null) {
            valid = false;
            wrongFields += "Seguimiento neurológico\n";
        }
        else if ((tracingHospitalRButton.getTag().toString().equals("Otros")) && ((tracingHospital.isEmpty()) || (tracingHospital.length() == 0) || (tracingHospital == null))) {
            valid = false;
            wrongFields += "Indique en que hospital/hospitales recibe seguimiento neurológico\n";
        }

        return valid;
    }

}
