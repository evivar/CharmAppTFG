package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadPatientByEmailResponse {

    @SerializedName("Paciente")
    @Expose
    protected Patient paciente;

    public Patient getPaciente() {
        return paciente;
    }

    public void setPaciente(Patient paciente) {
        this.paciente = paciente;
    }

}
