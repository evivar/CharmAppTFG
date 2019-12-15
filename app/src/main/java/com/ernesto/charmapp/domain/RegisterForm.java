package com.ernesto.charmapp.domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterForm implements Serializable {

    // Sección de datos personales

    private String email;

    private String phone;

    private String name;

    private String surname1;

    private String surname2;

    private String patient_id;

    private String birthDate;

    private String gender;

    private String profession;

    private String professionPlace;

    private String sector;

    private String otherSector;

    private String homeCP;

    private String jobCP;

    // Sección de preguntas generales

    // Sección de hábitos de vida

    private String smoking;

    private String alcohol;

    private String caffeine;

    private String diet;

    private String sport;

    // Sección de cualidad del dolor

    private String location;

    private String kind;

    private ArrayList<String> symptoms;

    public void completePersonalData(String email, String phone, String name, String surname1, String surname2, String patient_id, String birthDate, String gender, String profession,
                                     String professionPlace, String sector, String otherSector, String homeCP, String jobCP){
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.patient_id = patient_id;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profession = profession;
        this.professionPlace = professionPlace;
        this.sector = sector;
        this.otherSector = otherSector;
        this.homeCP = homeCP;
        this.jobCP = jobCP;
    }

    public void completeGeneralQuestions(){

    }

    public void completeLifeHabits(String smoking, String alcohol, String caffeine, String diet, String sport) {
        this.smoking = smoking;
        this.alcohol = alcohol;
        this.caffeine = caffeine;
        this.diet = diet;
        this.sport = sport;
    }

    public void completePainQuality (String location, String kind, ArrayList<String> symptoms) {
        this.location = location;
        this.kind = kind;
        this.symptoms = symptoms;
    }

    public void registerPatient(){

    }

    @NonNull
    @Override
    public String toString() {
        String res = "";
        res += email +"\n";
        res += phone +"\n";
        res += name +"\n";
        res += surname1 +"\n";
        res += surname2 +"\n";
        res += patient_id +"\n";
        res += birthDate +"\n";
        res += gender +"\n";
        res += profession +"\n";
        res += professionPlace +"\n";
        res += sector +"\n";
        res += otherSector +"\n";
        res += homeCP +"\n";
        res += jobCP +"\n";
        res += smoking +"\n";
        res += alcohol +"\n";
        res += caffeine +"\n";
        res += diet +"\n";
        res += sport +"\n";
        res += location +"\n";
        res += kind +"\n";
        res += symptoms +"\n";
        return res;
    }

}
