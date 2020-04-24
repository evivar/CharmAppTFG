package com.ernesto.charmapp.interactors.responses.diaryResponses;

import com.ernesto.charmapp.domain.retrofitEntities.Diary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadAllDiariesResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Resultado")
    @Expose
    private List<Diary> diaryList = null;

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

    public List<Diary> getDiaryList() {
        return diaryList;
    }

    public void setDiaryList(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

}