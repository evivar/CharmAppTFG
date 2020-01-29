package com.ernesto.charmapp.interactors.responses.patientResponses;

import com.ernesto.charmapp.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadAllPatientsResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Resultado")
    @Expose
    private List<Patient> patientList = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

}