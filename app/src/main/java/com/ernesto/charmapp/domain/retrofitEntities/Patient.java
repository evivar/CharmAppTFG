package com.ernesto.charmapp.domain.retrofitEntities;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase Patient
 * <p>
 * Entidad Patient de la base de datos
 * <p>
 * Clase generada usando <a href="http://www.jsonschema2pojo.org/" style="text-decoration:none; color:black"> jsonschema2pojo </a>
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class Patient implements Serializable {

    /**
     * ID del paciente
     */
    @SerializedName("patient_id")
    @Expose
    private String id;

    /**
     * Email del paciente
     */
    @SerializedName("email")
    @Expose
    private String email;

    /**
     * Contraseña del paciente
     */
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * Nombre del paciente
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Primer apellido del paciente
     */
    @SerializedName("surname1")
    @Expose
    private String surname1;

    /**
     * Segundo apellido del paciente
     */
    @SerializedName("surname2")
    @Expose
    private String surname2;

    /**
     * Fecha de inicio del estudio
     */
    @SerializedName("init_date")
    @Expose
    private String initDate;

    /**
     * Fecha de fin del estudio
     */
    @SerializedName("end_date")
    @Expose
    private String endDate;

    /**
     * Teléfono del paciente
     */
    @SerializedName("phone")
    @Expose
    private Integer phone;

    /**
     * Duración de las crisis del paciente
     */
    @SerializedName("ch_duration")
    @Expose
    private Integer ch_duration;

    /**
     * Constructor por defecto
     */
    public Patient() {
    }

    /**
     * Constructor de Patient
     *
     * @param id          ID del paciente
     * @param email       Email del paciente
     * @param password    Contraseña del paciente
     * @param name        Nombre del paciente
     * @param surname1    Primer apellido del paciente
     * @param surname2    Segundo apellido del paciente
     * @param initDate    Fecha de inicio del estudio
     * @param endDate     Fecha de fin del estudio
     * @param phone       Teléfono del paciente
     * @param ch_duration Duración de las crisis del paciente
     */
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

    /**
     * Devuelve el ID del paciente
     *
     * @return ID del paciente
     */
    public String getPatientId() {
        return id;
    }

    /**
     * Establece el ID del paciente
     *
     * @param id ID del paciente
     */
    public void setPatientId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el email del paciente
     *
     * @return Email del paciente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del paciente
     *
     * @param email Email del paciente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la contraseña del paciente
     *
     * @return Contraseña del paciente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del paciente
     *
     * @param password Contraseña del paciente
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Devuelve el nombre del paciente
     *
     * @return Nombre del paciente
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del paciente
     *
     * @param name Nombre del paciente
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve el primer apellido del paciente
     *
     * @return Primer apellido del paciente
     */
    public String getSurname1() {
        return surname1;
    }

    /**
     * Establece el primer apellido del paciente
     *
     * @param surname1 Primer apellido del paciente
     */
    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    /**
     * Devuelve el segundo apellido del paciente
     *
     * @return Segundo apellido del paciente
     */
    public String getSurname2() {
        return surname2;
    }

    /**
     * Establece el segundo apellido del paciente
     *
     * @param surname2 Segundo apellido del paciente
     */
    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    /**
     * Devuelve la fecha de inicio del estudio
     *
     * @return Fecha de inicio del estudio
     */
    public String getInitDate() {
        return initDate;
    }

    /**
     * Establece la fecha de inicio del estudio
     *
     * @param initDate Fecha de inicio del estudio
     */
    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    /**
     * Devuelve la fecha de fin del estudio
     *
     * @return Fecha de fin del estudio
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Establece la fecha de fin del estudio
     *
     * @param endDate Fecha de fin del estudio
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Devuelve el teléfono del paciente
     *
     * @return Teléfono del paciente
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     * Establece el teléfono del paciente
     *
     * @param phone Teléfono del paciente
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    /**
     * Devuelve la duración de las crisis del paciente
     *
     * @return Duración de las crisis del paciente
     */
    public Integer getCh_duration() {
        return ch_duration;
    }

    /**
     * Establece la duración de las crisis del paciente
     *
     * @param ch_duration Duración de las crisis del paciente
     */
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
