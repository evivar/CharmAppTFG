package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientLoginResponse {

    @SerializedName("Estado del error")
    @Expose
    private Boolean estadoDelError;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Paciente")
    @Expose
    private Patient paciente;

    public Boolean getEstadoDelError() {
        return estadoDelError;
    }

    public void setEstadoDelError(Boolean estadoDelError) {
        this.estadoDelError = estadoDelError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Patient getPaciente() {
        return paciente;
    }

    public void setPaciente(Patient paciente) {
        this.paciente = paciente;
    }
}
