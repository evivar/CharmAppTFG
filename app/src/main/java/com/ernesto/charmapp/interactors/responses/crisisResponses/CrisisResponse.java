package com.ernesto.charmapp.interactors.responses.crisisResponses;

import com.ernesto.charmapp.domain.Headache;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrisisResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Resultado")
    @Expose
    private Headache crisis;

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

    public Headache getCrisis() {
        return crisis;
    }

    public void setCrisis(Headache crisis) {
        this.crisis = crisis;
    }

}