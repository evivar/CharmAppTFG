package com.ernesto.charmapp.data;

import com.ernesto.charmapp.interactors.responses.ActiveCrisisResponse;
import com.ernesto.charmapp.interactors.responses.ChangePasswordResponse;
import com.ernesto.charmapp.interactors.responses.CreateCrisisResponse;
import com.ernesto.charmapp.interactors.responses.CreateDiaryResponse;
import com.ernesto.charmapp.interactors.responses.DoctorLoginResponse;
import com.ernesto.charmapp.interactors.responses.PatientLoginResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllCrisisResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllDiariesResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllPatientActiveCrisisResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllPatientCrisisByDateResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllPatientCrisisResponse;
import com.ernesto.charmapp.interactors.responses.ReadAllPatientsResponse;
import com.ernesto.charmapp.interactors.responses.ReadDiaryResponse;
import com.ernesto.charmapp.interactors.responses.ReadPasswordByEmailResponse;
import com.ernesto.charmapp.interactors.responses.ReadPatientActiveCrisisResponse;
import com.ernesto.charmapp.interactors.responses.ReadPatientByEmailResponse;
import com.ernesto.charmapp.interactors.responses.UpdateCrisisResponse;
import com.ernesto.charmapp.interactors.responses.UpdateDiaryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APICalls {

    /**
     * POST REQUEST:
     * @FormUrlEncoded <- Siempre que haya parametros
     * @POST("endpoint")
     * Call<ResponseBody>nombre(@Field("nombreCampo") Tipo nombre)
     */

    /**
     * PUT REQUEST:
     *
     * @FormUrlEncoded <- Siempre que haya parametros
     * @PUT("endpoint") Call<ResponseBody>nombre(@Field("nombreCampo") Tipo nombre)
     */

    /********** PATIENT **********/
    /**
     * TODO:
     *      createPatient: Con/sin patient_id y Con/sin password
     *      patientLogin
     *      readAllPatients
     *      readPatientByEmail
     *      readPasswordByEmail
     *      updatePatientPassword
     *      deletePatient
     */

    // Terminar bien el createPatient
    @FormUrlEncoded
    @POST("patientLogin")
    Call<PatientLoginResponse> patientLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("updatePatientPassword")
    Call<ChangePasswordResponse> changePassword(
            @Field("email") String email,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    @GET("readAllPatients")
    Call<ReadAllPatientsResponse> readAllPatients();

    @FormUrlEncoded
    @POST("readPatientByEmail")
    Call<ReadPatientByEmailResponse> readPatientByEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("readPasswordByEmail")
    Call<ReadPasswordByEmailResponse> readPasswordByEmail(
            @Field("email") String email
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
    Call<CreateDiaryResponse> createDiary(
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
    @POST("readAllPatientDiaries")
    Call<ReadAllDiariesResponse> readAllPatientDiaries(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readLastDiary")
    Call<ReadDiaryResponse> readLastDiary(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readDiaryByDate")
    Call<ReadDiaryResponse> readDiaryByDate(
            @Field("patient_id") String patient_id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("updateDiary")
    Call<UpdateDiaryResponse> updateDiary(
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
    @POST("createHeadache")
    Call<CreateCrisisResponse> createCrisis(
            @Field("patient_id") String patient_id,
            @Field("ch_id") String ch_id, // TODO: Eliminar el ch_id
            @Field("start_datetime") String start_datetime,
            @Field("end_datetime") String end_datatime,
            @Field("sport") String sport,
            @Field("alcohol") String alcohol,
            @Field("smoke") String smoke,
            @Field("medication") String medication,
            @Field("feeling") String feeling,
            @Field("pain_scale") int pain_scale
    );

    @FormUrlEncoded
    @POST("activeCrisis")
    Call<ActiveCrisisResponse> activeCrisis(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readPatientActiveCrisis")
    Call<ReadPatientActiveCrisisResponse> readPatientActiveCrisis(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("updateCrisis")
    Call<UpdateCrisisResponse> updateCrisis(
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
    @POST("readAllCrisisByPatientId")
    Call<ReadAllPatientCrisisResponse> readAllPatientCrisis(
            @Field("patient_id") String patient_id
    );

    @FormUrlEncoded
    @POST("readAllCrisisByDateAndPatientId")
    Call<ReadAllPatientCrisisByDateResponse> readAllPatientCrisisByDate(
            @Field("patient_id") String patient_id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("readAllActiveCrisisByPatientId")
    Call<ReadAllPatientActiveCrisisResponse> readAllPatientActiveCrisis(
            @Field("patient_id") String patient_id
    );


}


