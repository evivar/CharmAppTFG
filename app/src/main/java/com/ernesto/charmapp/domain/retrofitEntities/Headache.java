package com.ernesto.charmapp.domain.retrofitEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase Headache
 * <p>
 * Entidad Headache de la base de datos
 * <p>
 * Clase generada usando <a href="http://www.jsonschema2pojo.org/" style="text-decoration:none; color:black"> jsonschema2pojo </a>
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class Headache implements Serializable {

    /**
     * ID del paciente
     */
    @SerializedName("patient_id")
    @Expose
    private String patientId;

    /**
     * ID de la crisis
     *
     * @deprecated No se emplea este atributo desde la migración de la base de datos a AWS
     */
    @SerializedName("ch_id")
    @Expose
    private String chId;

    /**
     * Fecha de inicio de la crisis
     */
    @SerializedName("start_datetime")
    @Expose
    private String startDatetime;

    /**
     * Fecha de fin de la crisis
     */
    @SerializedName("end_datetime")
    @Expose
    private String endDatetime;

    /**
     * Deporte realizado
     */
    @SerializedName("sport")
    @Expose
    private String sport;

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
     * Medicación tomada
     */
    @SerializedName("medication")
    @Expose
    private String medication;

    /**
     * Sensaciones durante la crisis
     */
    @SerializedName("feeling")
    @Expose
    private String feeling;

    /**
     * Escala del 1 al 10 del dolor de la crisis
     */
    @SerializedName("pain_scale")
    @Expose
    private Integer painScale;

    /**
     * Constructor por defecto
     */
    public Headache() {
    }

    /**
     * Constructor de Headache
     *
     * @param patientId     ID del paciente
     * @param startDatetime Fecha de inicio de la crisis
     * @param endDatetime   Fecha de fin de la crisis
     * @param sport         Tiempo de deporte
     * @param alcohol       Alcohol ingerido
     * @param smoke         Tabaco fumado
     * @param medication    Medicación tomada
     * @param feeling       Sensaciones durante la crisis
     * @param painScale     Escala del 1 al 10 del dolor de la crisis
     */
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
     * Devuelve el ID de la crisis
     *
     * @return ID de la crisis
     */
    public String getChId() {
        return chId;
    }

    /**
     * Establece el ID de la crisis
     *
     * @param chId ID de la crisis
     */
    public void setChId(String chId) {
        this.chId = chId;
    }

    /**
     * Devuelve la fecha de inicio de la crisis
     *
     * @return Fecha de inicio de la crisis
     */
    public String getStartDatetime() {
        return startDatetime;
    }

    /**
     * Establece la fecha de inicio de la crisis
     *
     * @param startDatetime Fecha de inicio de la crisis
     */
    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    /**
     * Devuelve la fecha de fin de la crisis
     *
     * @return Fecha de fin de la crisis
     */
    public String getEndDatetime() {
        return endDatetime;
    }

    /**
     * Establece la fecha de fin de la crisis
     *
     * @param endDatetime Fecha de fin de la crisis
     */
    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    /**
     * Devuelve el tiempo de deporte realizado
     *
     * @return Tiempo de deporte realizado
     */
    public String getSport() {
        return sport;
    }

    /**
     * Establece el tiempo de deporte realizado
     *
     * @param sport Tiempo de deporte realizado
     */
    public void setSport(String sport) {
        this.sport = sport;
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
     * Devuelve la medicacion tomada
     *
     * @return Medicación tomada
     */
    public String getMedication() {
        return medication;
    }

    /**
     * Establece la medicación tomada
     *
     * @param medication Medicación tomada
     */
    public void setMedication(String medication) {
        this.medication = medication;
    }

    /**
     * Devuelve las sensaciones del paciente durante la crisis
     *
     * @return Sensaciones del paciente durante la crisis
     */
    public String getFeeling() {
        return feeling;
    }

    /**
     * Establece las sensaciones del paciente durante la crisis
     *
     * @param feeling Sensaciones del paciente durante la crisis
     */
    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    /**
     * Devuelve el nivel de dolor de la crisis
     *
     * @return Nivel de dolor de la crisis
     */
    public Integer getPainScale() {
        return painScale;
    }

    /**
     * Establece el nivel de dolor de la ciris
     *
     * @param painScale Nivel de dolor de la crisis
     */
    public void setPainScale(Integer painScale) {
        this.painScale = painScale;
    }

}
