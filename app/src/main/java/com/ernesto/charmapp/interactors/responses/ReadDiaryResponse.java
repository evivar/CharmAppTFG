package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Diary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadDiaryResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Diario")
    @Expose
    private Diary diario;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Diary getDiario() {
        return diario;
    }

    public void setDiario(Diary diario) {
        this.diario = diario;
    }

}
