package com.ernesto.charmapp.interactors.responses.diaryResponses;

import com.ernesto.charmapp.domain.retrofitEntities.Diary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiaryResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Resultado")
    @Expose
    private Diary diary;

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

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

}