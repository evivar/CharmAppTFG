package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadAllPatientsResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Pacientes")
    @Expose
    private List<Patient> pacientes = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Patient> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Patient> pacientes) {
        this.pacientes = pacientes;
    }

}
