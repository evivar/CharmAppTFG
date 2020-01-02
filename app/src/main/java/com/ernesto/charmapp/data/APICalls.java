package com.ernesto.charmapp.data;

import com.ernesto.charmapp.interactors.responses.DoctorLoginResponse;
import com.ernesto.charmapp.interactors.responses.UpdateResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.ReadAllCrisisResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.ReadAllDiariesResponse;
import com.ernesto.charmapp.interactors.responses.patientResponses.PatientResponse;
import com.ernesto.charmapp.interactors.responses.patientResponses.ReadAllPatientsResponse;
import com.ernesto.charmapp.interactors.responses.patientResponses.ReadPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APICalls {

    /********** PATIENT **********/

    @FormUrlEncoded
    @POST("createPatient_login")
    Call<PatientResponse> createPatient_login(
            @Field("patient_id") String patient_id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("surname1") String surname1,
            @Field("surname2") String surname2,
            @Field("init_date") String init_date,
            @Field("end_date") String end_date,
            @Field("phone") int phone
    );

    @FormUrlEncoded
    @POST("createPatient")
    Call<PatientResponse> createPatient(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("surname1") String surname1,
            @Field("surname2") String surname2,
            @Field("init_date") String init_date,
            @Field("end_date") String end_date,
            @Field("phone") int phone
    );

    @GET("readAllPatients")
    Call<ReadAllPatientsResponse> readAllPatients();

    @FormUrlEncoded
    @POST("readPatientByEmail")
    Call<PatientResponse> readPatientByEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("readPatientById")
    Call<PatientResponse> readPatientById(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readPasswordByEmail")
    Call<ReadPasswordResponse> readPasswordByEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatePatientPassword")
    Call<UpdateResponse> changePassword(
            @Field("email") String email,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    @FormUrlEncoded
    @POST("patientLogin")
    Call<PatientResponse> patientLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    /********** DOCTOR **********/

    @FormUrlEncoded
    @POST("doctorLogin")
    Call<DoctorLoginResponse> doctorLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    /********** DIARY **********/

    @FormUrlEncoded
    @POST("createDiary")
    Call<DiaryResponse> createDiary(
            @Field("patient_id") String patient_id,
            @Field("date") String date,
            @Field("sleep_time") String sleep_time,
            @Field("change_residence") String change_residence,
            @Field("sport_time") String sport_time,
            @Field("alcohol") String alcohol,
            @Field("smoke") String smoke,
            @Field("feeling") String feeling
    );

    @GET("readAllDiaries")
    Call<ReadAllDiariesResponse> readAllDiaries();

    @FormUrlEncoded
    @POST("readAllDiariesByEmail")
    Call<ReadAllDiariesResponse> readAllDiariesByEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("readAllDiariesById")
    Call<ReadAllDiariesResponse> readAllDiariesById(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readLastDiary")
    Call<DiaryResponse> readLastDiary(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readDiaryByDate")
    Call<DiaryResponse> readDiaryByDate(
            @Field("patient_id") String patient_id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("updateDiary")
    Call<UpdateResponse> updateDiary(
            @Field("patient_id") String patient_id,
            @Field("date") String date,
            @Field("sleep_time") String sleep_time,
            @Field("sport_time") String sport_time,
            @Field("alcohol") String alcohol,
            @Field("smoke") String smoke,
            @Field("feeling") String feeling
    );

    /********** HEADACHE **********/

    @FormUrlEncoded
    @POST("createCrisis")
    Call<CrisisResponse> createCrisis(
            @Field("patient_id") String patient_id,
            @Field("start_datetime") String start_datetime,
            @Field("end_datetime") String end_datatime,
            @Field("sport") String sport,
            @Field("alcohol") String alcohol,
            @Field("smoke") String smoke,
            @Field("medication") String medication,
            @Field("feeling") String feeling,
            @Field("pain_scale") int pain_scale
    );

    @GET("readAllCrisis")
    Call<ReadAllCrisisResponse> readAllCrisis();

    @FormUrlEncoded
    @POST("readAllCrisisByEmail")
    Call<ReadAllCrisisResponse> readAllCrisisByEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("readAllCrisisById")
    Call<ReadAllCrisisResponse> readAllCrisisById(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readCrisisByDate")
    Call<CrisisResponse> readCrisisByDate(
            @Field("patient_id") String patient_id,
            @Field("date") String date
    );


    @GET("readAllActiveCrisis")
    Call<ReadAllCrisisResponse> readAllActiveCrisis();

    @FormUrlEncoded
    @POST("readActiveCrisisByEmail")
    Call<CrisisResponse> readActiveCrisisByEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("readActiveCrisisById")
    Call<CrisisResponse> readActiveCrisisById(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("updateCrisis")
    Call<UpdateResponse> updateCrisis(
            @Field("patient_id") String patient_id,
            @Field("start_datetime") String start_datetime,
            @Field("end_datetime") String end_datatime,
            @Field("sport") String sport,
            @Field("alcohol") String alcohol,
            @Field("smoke") String smoke,
            @Field("medication") String medication,
            @Field("feeling") String feeling,
            @Field("pain_scale") int pain_scale
    );

}


