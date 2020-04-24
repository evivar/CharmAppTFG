package com.ernesto.charmapp.interactors.responses.stationResponses;

import com.ernesto.charmapp.domain.retrofitEntities.StationRetrofit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadAllStationsResponse {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Resultado")
    @Expose
    private List<StationRetrofit> stations = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<StationRetrofit> getStations() {
        return stations;
    }

    public void setStations(List<StationRetrofit> stations) {
        this.stations = stations;
    }

}
