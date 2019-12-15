package com.ernesto.charmapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Headache implements Serializable {

    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("ch_id")
    @Expose
    private String chId;
    @SerializedName("start_datetime")
    @Expose
    private String startDatetime;
    @SerializedName("end_datetime")
    @Expose
    private String endDatetime;
    @SerializedName("sport")
    @Expose
    private String sport;
    @SerializedName("alcohol")
    @Expose
    private String alcohol;
    @SerializedName("smoke")
    @Expose
    private String smoke;
    @SerializedName("medication")
    @Expose
    private String medication;
    @SerializedName("feeling")
    @Expose
    private String feeling;
    @SerializedName("pain_scale")
    @Expose
    private Integer painScale;

    public Headache() {
    }

    public Headache(String patientId, String startDatetime, String endDatetime, String sport, String alcohol, String smoke, String medication, String feeling, Integer painScale) {
        this.patientId = patientId;
        this.chId = "1234567";
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.sport = sport;
        this.alcohol = alcohol;
        this.smoke = smoke;
        this.medication = medication;
        this.feeling = feeling;
        this.painScale = painScale;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getChId() {
        return chId;
    }

    public void setChId(String chId) {
        this.chId = chId;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public Integer getPainScale() {
        return painScale;
    }

    public void setPainScale(Integer painScale) {
        this.painScale = painScale;
    }

}
