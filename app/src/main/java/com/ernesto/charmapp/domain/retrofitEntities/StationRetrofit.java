package com.ernesto.charmapp.domain.retrofitEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationRetrofit {

    @SerializedName("station_id")
    @Expose
    private String stationId;
    @SerializedName("station_url_id")
    @Expose
    private String stationUrlId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("web_source")
    @Expose
    private String webSource;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("altitude")
    @Expose
    private Integer altitude;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationUrlId() {
        return stationUrlId;
    }

    public void setStationUrlId(String stationUrlId) {
        this.stationUrlId = stationUrlId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebSource() {
        return webSource;
    }

    public void setWebSource(String webSource) {
        this.webSource = webSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
