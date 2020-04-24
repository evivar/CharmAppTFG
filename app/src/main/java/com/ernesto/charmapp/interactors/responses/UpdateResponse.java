package com.ernesto.charmapp.interactors.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase UpdateResponse
 * <p>
 * Clase que representa la respuesta de las peticiones de las operaciones "Update" a la API
 * <p>
 * Clase generada usando <a href="http://www.jsonschema2pojo.org/" style="text-decoration:none; color:black"> jsonschema2pojo </a>
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class UpdateResponse {

    /**
     * Error de la respuesta
     */
    @SerializedName("Error")
    @Expose
    private Boolean error;

    /**
     * Mensaje de la respuesta
     */
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    /**
     * Devuelve el error de la respuesta
     *
     * @return Error de la respuesta
     */
    public Boolean getError() {
        return error;
    }

    /**
     * Establece el error de la respuesta
     * @param error Error de la respuesta
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * Devuelve el mensaje de la respuesta
     * @return Mensaje de la respuesta
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de la respuesta
     * @param mensaje Mensaje de la respuesta
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}