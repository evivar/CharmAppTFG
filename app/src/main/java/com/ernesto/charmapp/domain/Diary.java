package com.ernesto.charmapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Diary implements Serializable {

    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("diary_id")
    @Expose
    private String diaryId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("sleep_time")
    @Expose
    private String sleepTime;
    @SerializedName("change_residence")
    @Expose
    private String changeResidence;
    @SerializedName("sport_time")
    @Expose
    private String sportTime;
    @SerializedName("alcohol")
    @Expose
    private String alcohol;
    @SerializedName("smoke")
    @Expose
    private String smoke;
    @SerializedName("feeling")
    @Expose
    private String feeling;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getChangeResidence() {
        return changeResidence;
    }

    public void setChangeResidence(String changeResidence) {
        this.changeResidence = changeResidence;
    }

    public String getSportTime() {
        return sportTime;
    }

    public void setSportTime(String sportTime) {
        this.sportTime = sportTime;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

}
