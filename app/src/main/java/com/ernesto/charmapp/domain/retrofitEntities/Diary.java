package com.ernesto.charmapp.domain.retrofitEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase Diary
 * <p>
 * Entidad Diary de la base de datos
 * <p>
 * Clase generada usando <a href="http://www.jsonschema2pojo.org/" style="text-decoration:none; color:black"> jsonschema2pojo </a>
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class Diary implements Serializable {

    /**
     * ID del paciente
     */
    @SerializedName("patient_id")
    @Expose
    private String patientId;

    /**
     * ID del diario
     */
    @SerializedName("diary_id")
    @Expose
    private String diaryId;

    /**
     * Fecha del diario
     */
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * Tiempo de sueño
     */
    @SerializedName("sleep_time")
    @Expose
    private String sleepTime;

    /**
     * Cambio de residencia
     */
    @SerializedName("change_residence")
    @Expose
    private String changeResidence;

    /**
     * Tiempo de deporte
     */
    @SerializedName("sport_time")
    @Expose
    private String sportTime;

    /**
     * Alcohol ingerido
     */
    @SerializedName("alcohol")
    @Expose
    private String alcohol;

    /**
     * Tabaco fumado
     */
    @SerializedName("smoke")
    @Expose
    private String smoke;

    /**
     * Sensación del paciente
     */
    @SerializedName("feeling")
    @Expose
    private String feeling;

    /**
     * Constructor por defecto
     */
    public Diary() {
    }

    /**
     * Constructor para la entidad Diary
     *
     * @param patientId       ID del paciente
     * @param diaryId         ID del diario
     * @param date            Fecha del diario
     * @param sleepTime       Tiempo de sueño del paciente
     * @param changeResidence Cambio de residencia del paciente
     * @param sportTime       Tiempo de deporte
     * @param alcohol         Alcohol Ingerido
     * @param smoke           Tabaco fumado
     * @param feeling         Sensación del paciente
     */
    public Diary(String patientId, String diaryId, String date, String sleepTime, String changeResidence, String sportTime, String alcohol, String smoke, String feeling) {
        this.patientId = patientId;
        this.diaryId = diaryId;
        this.date = date;
        this.sleepTime = sleepTime;
        this.changeResidence = changeResidence;
        this.sportTime = sportTime;
        this.alcohol = alcohol;
        this.smoke = smoke;
        this.feeling = feeling;
    }

    /**
     * Constructor para la entidad Diary
     *
     * @param patientId       ID del paciente
     * @param date            Fecha del diario
     * @param sleepTime       Tiempo de sueño del paciente
     * @param changeResidence Cambio de residencia del paciente
     * @param sportTime       Tiempo de deporte
     * @param alcohol         Alcohol Ingerido
     * @param smoke           Tabaco fumado
     * @param feeling         Sensación del paciente
     */
    public Diary(String patientId, String date, String sleepTime, String changeResidence, String sportTime, String alcohol, String smoke, String feeling) {
        this.patientId = patientId;
        this.date = date;
        this.sleepTime = sleepTime;
        this.changeResidence = changeResidence;
        this.sportTime = sportTime;
        this.alcohol = alcohol;
        this.smoke = smoke;
        this.feeling = feeling;
    }

    /**
     * Devuelve el ID del paciente
     *
     * @return ID del paciente
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Establece el ID del paciente
     *
     * @param patientId ID del paciente
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Devuelve el ID del diario
     *
     * @return ID del diario
     */
    public String getDiaryId() {
        return diaryId;
    }

    /**
     * Establece el ID del diario
     *
     * @param diaryId ID del diario
     */
    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    /**
     * Devuelve la fecha del diario
     *
     * @return Fecha del diario
     */
    public String getDate() {
        return date;
    }

    /**
     * Establece la fecha del diario
     *
     * @param date Fecha del diario
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Devuelve el tiempo de sueño del paciente
     *
     * @return Tiempo de sueño del paciente
     */
    public String getSleepTime() {
        return sleepTime;
    }

    /**
     * Establece el tiempo de sueño del paciente
     *
     * @param sleepTime Tiempo de sueño del paciente
     */
    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    /**
     * Devuelve el cambio de residencia del paciente
     *
     * @return Cambio de residencia del paciente
     */
    public String getChangeResidence() {
        return changeResidence;
    }

    /**
     * Establece el cambio de residencia del paciente
     *
     * @param changeResidence Cambio de residencia del paciente
     */
    public void setChangeResidence(String changeResidence) {
        this.changeResidence = changeResidence;
    }

    /**
     * Devuelve el tiempo de deporte
     *
     * @return Tiempo de deporte
     */
    public String getSportTime() {
        return sportTime;
    }

    /**
     * Establece el tiempo de deporte
     *
     * @param sportTime Tiempo de deporte
     */
    public void setSportTime(String sportTime) {
        this.sportTime = sportTime;
    }

    /**
     * Devuelve el alcohol ingerido
     *
     * @return Alcohol ingerido
     */
    public String getAlcohol() {
        return alcohol;
    }

    /**
     * Establece el alcohol ingerido
     *
     * @param alcohol Alcohol ingerido
     */
    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    /**
     * Devuelve el tabaco fumado
     *
     * @return Tabaco fumado
     */
    public String getSmoke() {
        return smoke;
    }

    /**
     * Establece el tabaco fumado
     *
     * @param smoke Tabaco fumado
     */
    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    /**
     * Devuelve la sensación del paciente
     *
     * @return Sensación del paciente
     */
    public String getFeeling() {
        return feeling;
    }

    /**
     * Establece la sensación del paciente
     *
     * @param feeling Sensación del paciente
     */
    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

}
