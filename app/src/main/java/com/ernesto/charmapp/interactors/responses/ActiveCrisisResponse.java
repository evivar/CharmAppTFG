package com.ernesto.charmapp.interactors.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveCrisisResponse {

    @SerializedName("Estado del error")
    @Expose
    private Boolean estadoDelError;
    @SerializedName("Crisis activa")
    @Expose
    private boolean crisisActiva;

    public Boolean getEstadoDelError() {
        return estadoDelError;
    }

    public void setEstadoDelError(Boolean estadoDelError) {
        this.estadoDelError = estadoDelError;
    }

    public boolean getCrisisActiva() {
        return crisisActiva;
    }

    public void setCrisisActiva(boolean crisisActiva) {
        this.crisisActiva = crisisActiva;
    }

}
