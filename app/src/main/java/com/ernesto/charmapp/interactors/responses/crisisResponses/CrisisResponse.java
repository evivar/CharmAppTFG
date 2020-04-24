package com.ernesto.charmapp.interactors.responses.crisisResponses;

import com.ernesto.charmapp.domain.retrofitEntities.Headache;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase CrisisResponse
 * <p>
 * Clase que representa las respuestas de las peticiones a la API de las crisis
 * <p>
 * Clase generada usando <a href="http://www.jsonschema2pojo.org/" style="text-decoration:none; color:black"> jsonschema2pojo </a>
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class CrisisResponse {

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
     * Resultado de la respuesta
     */
    @SerializedName("Resultado")
    @Expose
    private Headache crisis;

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
     *
     * @param error Error de la respuesta
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * Devuelve el mensaje de la respuesta
     *
     * @return Mensaje de la respuesta
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de la respuesta
     *
     * @param mensaje Mensaje de la respuesta
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Devuelve la crisis de la respuesta
     *
     * @return Crisis de la respuesta
     */
    public Headache getCrisis() {
        return crisis;
    }

    /**
     * Establece la crisis de la respuesta
     *
     * @param crisis Crisis de la respuesta
     */
    public void setCrisis(Headache crisis) {
        this.crisis = crisis;
    }

}