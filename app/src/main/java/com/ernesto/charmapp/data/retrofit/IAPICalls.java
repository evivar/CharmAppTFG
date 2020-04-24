package com.ernesto.charmapp.data.retrofit;

import com.ernesto.charmapp.interactors.responses.UpdateResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.interactors.responses.crisisResponses.ReadAllCrisisResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;
import com.ernesto.charmapp.interactors.responses.diaryResponses.ReadAllDiariesResponse;
import com.ernesto.charmapp.interactors.responses.patientResponses.PatientResponse;
import com.ernesto.charmapp.interactors.responses.patientResponses.ReadAllPatientsResponse;
import com.ernesto.charmapp.interactors.responses.patientResponses.ReadPasswordResponse;
import com.ernesto.charmapp.interactors.responses.stationResponses.ReadAllStationsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Interfaz APICalls
 * <p>
 * Interfaz con todas las llamadas a la API usando Retrofit2
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 * @see <a href="https://square.github.io/retrofit/"> Página oficial de Retrofit </a>
 * @see <a href="https://www.getpostman.com/collections/962b903a217927f72e3e> Postman API para probar las llamadas <span style="color: red"> Es necesario tener <a href="https://www.postman.com/downloads/">Postman </a> instalado </span></a>
 */
public interface IAPICalls {

    // <editor-fold desc="Llamadas del paciente">

    // <editor-fold desc="GET">

    /**
     * Llamada a la API que lee todos los pacientes de la base de datos
     *
     * @return Lista con todos los pacientes leidos
     */
    @GET("readAllPatients")
    Call<ReadAllPatientsResponse> readAllPatients();

    // </editor-fold>

    // <editor-fold desc="POST">

    /**
     * Llamada a la API que crea e inserta un paciente en la base de datos
     *
     * @param email     Email del usuario
     * @param password  Contraseña del usuario
     * @param name      Nombre del usuario
     * @param surname1  Primer apellido del usuario
     * @param surname2  Segundo apellido del usuario
     * @param init_date Fecha de inicio del estudio
     * @param end_date  Fecha de fin del estudio
     * @param phone     Teléfono del usuario
     * @return Mensaje con el resultado de la operación
     */
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

    /**
     * Llamada a la API que lee un paciente de la base de datos dado un email
     *
     * @param email Email del paciente a leer
     * @return Paciente leido
     */
    @FormUrlEncoded
    @POST("readPatientByEmail")
    Call<PatientResponse> readPatientByEmail(
            @Field("email") String email
    );

    /**
     * Llamada a la API que lee un paciente de la base de datos dado un ID
     *
     * @param patient_id ID del paciente a leer
     * @return Paciente leido
     */
    @FormUrlEncoded
    @POST("readPatientById")
    Call<PatientResponse> readPatientById(
            @Field("patient_id") String patient_id
    );

    /**
     * Llamada a la API que comprueba si un usuario es válido y lo logea dados un email y una contraseña
     *
     * @param email    Email del usuario a logear
     * @param password Contraseña del usuario a logear
     * @return Resultado de la operación
     */
    @FormUrlEncoded
    @POST("login")
    Call<PatientResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    /**
     * Llamada a la API que lee una contraseña de la base de datos dado el ID del usuario al que pertenece
     *
     * @param patient_id ID del paciente
     * @return Contraseña leida
     */
    @FormUrlEncoded
    @POST("readPasswordById")
    Call<ReadPasswordResponse> readPasswordById(
            @Field("patient_id") String patient_id
    );

    // </editor-fold>

    // <editor-fold desc="PUT">

    /**
     * Llamada a la API que actualiza la contraseña de un usuario dado su email, su contraseña actual y su contraseña nueva
     *
     * @param email        Email del paciente a actualizar la contraseña
     * @param old_password Contraseña acutal del paciente
     * @param new_password Nueva contraseña del paciente
     * @return Resultado de la operación
     */
    @FormUrlEncoded
    @POST("updatePatientPassword")
    Call<UpdateResponse> updatePatientPassword(
            @Field("email") String email,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    // </editor-fold>

    // <editor-fold desc="DELETE">

    // </editor-fold>

    // </editor-fold>

    // <editor-fold desc="Llamadas del diario">

    // <editor-fold desc="GET">

    /**
     * Llamada a la API que lee todos los diarios de la base de datos
     *
     * @return Lista de los diarios leidos
     */
    @GET("readAllDiaries")
    Call<ReadAllDiariesResponse> readAllDiaries();

    // </editor-fold>

    // <editor-fold desc="POST">

    /**
     * Llamada a la API que crea e inserta un diario en la base de datos
     *
     * @param patient_id       ID del paciente
     * @param date             Fecha del diario
     * @param sleep_time       Tiempo de sueño
     * @param change_residence Cambio de residencia
     * @param sport_time       Tiempo de deporte
     * @param alcohol          Alcohol ingerido
     * @param smoke            Tabaco fumado
     * @param feeling          Sensación del paciente
     * @return Resultado de la operación
     */
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

    /**
     * Llamada a la API que lee todos los diarios de un paciente dado su email
     *
     * @param email Email del paciente
     * @return Lista con los diarios leidos
     */
    @FormUrlEncoded
    @POST("readAllDiariesByEmail")
    Call<ReadAllDiariesResponse> readAllDiariesByEmail(
            @Field("email") String email
    );

    /**
     * Llamada a la API que lee todos los diarios de un paciente dado su ID
     *
     * @param patient_id ID del paciente
     * @return Lista con los diarios leidos
     */
    @FormUrlEncoded
    @POST("readAllDiariesById")
    Call<ReadAllDiariesResponse> readAllDiariesById(
            @Field("patient_id") String patient_id
    );

    /**
     * Llamada a la API que lee el ultimo diario de la base de datos de un paciente dado su ID
     *
     * @param patient_id ID del paciente
     * @return Diario leido
     */
    @FormUrlEncoded
    @POST("readLastDiary")
    Call<DiaryResponse> readLastDiary(
            @Field("patient_id") String patient_id
    );

    /**
     * Llamada a la API que lee un diario de la base de datos dados el ID del paciente y la fecha del diario
     *
     * @param patient_id ID del paciente
     * @param date       Fecha del diario
     * @return Diario leido
     */
    @FormUrlEncoded
    @POST("readDiaryByDate")
    Call<DiaryResponse> readDiaryByDate(
            @Field("patient_id") String patient_id,
            @Field("date") String date
    );

    // </editor-fold>

    // <editor-fold desc="PUT">

    /**
     * Llamada a la API que actualiza la información del diario
     *
     * @param patient_id       ID del paciente
     * @param date             Fecha del diario
     * @param sleep_time       Tiempo de sueño
     * @param change_residence Cambio de residencia
     * @param sport_time       Tiempo de deporte
     * @param alcohol          Alcohol ingerido
     * @param smoke            Tabaco fumado
     * @param feeling          Sensación del paciente
     * @return Resultado de la operación
     */
    @FormUrlEncoded
    @POST("updateDiary")
    Call<UpdateResponse> updateDiary(
            @Field("patient_id") String patient_id,
            @Field("date") String date,
            @Field("sleep_time") String sleep_time,
            @Field("change_residence") String change_residence,
            @Field("sport_time") String sport_time,
            @Field("alcohol") String alcohol,
            @Field("smoke") String smoke,
            @Field("feeling") String feeling
    );

    // </editor-fold>

    // <editor-fold desc="DELETE">

    // </editor-fold>

    // </editor-fold>

    // <editor-fold desc="Llamadas de la crisis">

    // <editor-fold desc="GET">

    /**
     * Llamada a la API que lee todas las crisis de la base de datos
     *
     * @return Lista de las crisis leidas
     */
    @GET("readAllCrisis")
    Call<ReadAllCrisisResponse> readAllCrisis();

    /**
     * Llamada a la API que lee todas las crisis activas(con end_datetime igual a null o igual a '0000-00-00') de la base de datos
     *
     * @return Lista de las crisis activas leidas
     */
    @GET("readAllActiveCrisis")
    Call<ReadAllCrisisResponse> readAllActiveCrisis();

    // </editor-fold>

    // <editor-fold desc="POST">

    /**
     * Llamada a la API que crea e inserta una nueva crisis en la base de datos
     *
     * @param patient_id     ID del paciente
     * @param start_datetime Fecha de inicio de la crisis
     * @param end_datatime   Fecha de fin de la crisis, puede ser null o '0000-00-00' si aún no ha terminado la crisis
     * @param sport          Deporte realizado
     * @param alcohol        Alcohol ingerido
     * @param smoke          Tabaco fumado
     * @param medication     Medicación tomada
     * @param feeling        Sensacion del paciente durante la crisis
     * @param pain_scale     Escala del 1 al 10 del dolor de la crisis
     * @return Resultado de la operación
     */
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

    /**
     * Llamada a la API que lee todas las crisis de un paciente de la base de datos dado su email
     *
     * @param email Email del paciente
     * @return Lista de las crisis leidas
     */
    @FormUrlEncoded
    @POST("readAllCrisisByEmail")
    Call<ReadAllCrisisResponse> readAllCrisisByEmail(
            @Field("email") String email
    );

    /**
     * Llamada a la API que lee todas las crisis de un paciente de la base de datos dado su ID
     *
     * @param patient_id ID del paciente
     * @return Lista de las crisis leidas
     */
    @FormUrlEncoded
    @POST("readAllCrisisById")
    Call<ReadAllCrisisResponse> readAllCrisisById(
            @Field("patient_id") String patient_id
    );

    /**
     * Llamada a la API que lee una crisis de la base de datos dados un ID y una fecha
     *
     * @param patient_id ID del paciente
     * @param date       Fecha de la crisis
     * @return Crisis leida
     */
    @FormUrlEncoded
    @POST("readCrisisByDate")
    Call<CrisisResponse> readCrisisByDate(
            @Field("patient_id") String patient_id,
            @Field("date") String date
    );

    /**
     * Llamada a la API que lee todas las crisis activas de un usuario de la base de datos dado su email
     *
     * @param email Email del usuario
     * @return Lista de las crisis activas leidas
     */
    @FormUrlEncoded
    @POST("readActiveCrisisByEmail")
    Call<CrisisResponse> readActiveCrisisByEmail(
            @Field("email") String email
    );

    /**
     * Llamada a la API que lee todas las crisis activas de un usuario de la base de datos dado su ID
     *
     * @param patient_id ID del paciente
     * @return Lista de las crisis activas leidas
     */
    @FormUrlEncoded
    @POST("readActiveCrisisById")
    Call<CrisisResponse> readActiveCrisisById(
            @Field("patient_id") String patient_id
    );

    // </editor-fold>

    // <editor-fold desc="PUT">

    /**
     * Llamada a la API que actualiza la información de la crisis
     *
     * @param patient_id     ID del paciente
     * @param start_datetime Fecha de inicio de la crisis
     * @param end_datatime   Fecha de fin de la crisis, puede ser null o '0000-00-00' si aún no ha terminado la crisis
     * @param sport          Deporte realizado
     * @param alcohol        Alcohol ingerido
     * @param smoke          Tabaco fumado
     * @param medication     Medicación tomada
     * @param feeling        Sensacion del paciente durante la crisis
     * @param pain_scale     Escala del 1 al 10 del dolor de la crisis
     * @return Resultado de la operación
     */
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

    // </editor-fold>

    // <editor-fold desc="DELETE">

    // </editor-fold>

    // </editor-fold>

    // <editor-fold desc="Llamadas de las estaciones meteorológicas">

    // <editor-fold desc="GET">

    @GET("readAllStations")
    Call<ReadAllStationsResponse> readAllStations();

    // </editor-fold>

    // <editor-fold desc="POST">

    // </editor-fold>

    // <editor-fold desc="PUT">

    // </editor-fold>

    // <editor-fold desc="DELETE">

    // </editor-fold>

    // </editor-fold>


    @FormUrlEncoded
    @POST("testService")
    Call<CrisisResponse> testService(
            @Field("hora") String hora,
            @Field("mensaje") String mensaje
    );


}


// <editor-fold desc="Llamadas del ...">

// <editor-fold desc="GET">

// </editor-fold>

// <editor-fold desc="POST">

// </editor-fold>

// <editor-fold desc="PUT">

// </editor-fold>

// <editor-fold desc="DELETE">

// </editor-fold>

// </editor-fold>