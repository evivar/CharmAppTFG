package com.ernesto.charmapp.interactors.responses.crisisResponses;

import com.ernesto.charmapp.domain.retrofitEntities.Headache;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase ReadAllCrisisResponse
 * <p>
 * Clase que representa la respuesta de las peticiones de las operaciones "Read All" a la API de las crisis
 * <p>
 * Clase generada usando <a href="http://www.jsonschema2pojo.org/" style="text-decoration:none; color:black"> jsonschema2pojo </a>
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class ReadAllCrisisResponse {

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
    private List<Headache> crisisList = null;

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
     * Devuelve la lista de crisis de la respuesta
     *
     * @return Lista de crisis de la respuesta
     */
    public List<Headache> getCrisisList() {
        return crisisList;
    }

    /**
     * Establece la lista de crisis de la respuesta
     *
     * @param crisisList Lista de ceisis de la respuesta
     */
    public void setCrisisList(List<Headache> crisisList) {
        this.crisisList = crisisList;
    }

}