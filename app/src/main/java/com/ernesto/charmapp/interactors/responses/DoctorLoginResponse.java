package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Doctor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorLoginResponse {

    @SerializedName("Estado del error")
    @Expose
    private Boolean estadoDelError;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Doctor")
    @Expose
    private Doctor doctor;

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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

}
