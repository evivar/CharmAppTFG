package com.ernesto.charmapp.interactors.responses.crisisResponses;

import com.ernesto.charmapp.domain.Headache;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadAllCrisisResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Resultado")
    @Expose
    private List<Headache> crisisList = null;

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

    public List<Headache> getCrisisList() {
        return crisisList;
    }

    public void setCrisisList(List<Headache> crisisList) {
        this.crisisList = crisisList;
    }

}