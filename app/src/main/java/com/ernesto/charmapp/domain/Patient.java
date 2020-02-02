package com.ernesto.charmapp.domain;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Patient implements Serializable {

    @SerializedName("patient_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname1")
    @Expose
    private String surname1;
    @SerializedName("surname2")
    @Expose
    private String surname2;
    @SerializedName("init_date")
    @Expose
    private String initDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("ch_duration")
    @Expose
    private Integer ch_duration;

    public Patient(String id, String email, String password, String name, String surname1, String surname2, String initDate, String endDate, Integer phone, Integer ch_duration) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.initDate = initDate;
        this.endDate = endDate;
        this.phone = phone;
        this.ch_duration = ch_duration;
    }

    public String getPatientId() {
        return id;
    }

    public void setPatientId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getCh_duration() {
        return ch_duration;
    }

    public void setCh_duration(Integer ch_duration) {
        this.ch_duration = ch_duration;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "";
        result += this.getPatientId();
        result += "\n " + this.getName();
        result += "\n " + this.getSurname1();
        result += "\n " + this.getSurname2();
        result += "\n " + this.getEmail();
        result += "\n " + this.getPassword();
        result += "\n " + this.getPhone();
        return result;
    }

}
