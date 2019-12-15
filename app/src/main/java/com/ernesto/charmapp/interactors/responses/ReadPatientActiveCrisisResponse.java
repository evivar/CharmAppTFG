package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Headache;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadPatientActiveCrisisResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Crisis")
    @Expose
    private Headache crisis;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Headache getCrisis() {
        return crisis;
    }

    public void setCrisis(Headache crisis) {
        this.crisis = crisis;
    }

}
