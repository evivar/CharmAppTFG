package com.ernesto.charmapp.interactors.responses;

import com.ernesto.charmapp.domain.Diary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadAllDiariesResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Diarios")
    @Expose
    private List<Diary> diaries = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Diary> getDiarios() {
        return diaries;
    }

    public void setDiarios(List<Diary> diaries) {
        this.diaries = diaries;
    }


}
