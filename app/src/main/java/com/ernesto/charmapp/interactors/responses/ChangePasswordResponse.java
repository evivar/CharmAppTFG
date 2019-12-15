package com.ernesto.charmapp.interactors.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse {

    @SerializedName("Estado del error")
    @Expose
    private Boolean estadoDelError;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

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

}
