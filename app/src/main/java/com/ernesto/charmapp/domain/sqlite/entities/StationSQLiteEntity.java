package com.ernesto.charmapp.domain.sqlite.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "station_table")
public class StationSQLiteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int url_id;

    private String city;

    private String country;

    private String web_source;

    private String type;

    private double longitude;

    private double latitude;

    @Nullable
    private double altitude;

    private double cos_latitude;

    private double sin_latitude;

    private double cos_longitude;

    private double sin_longitude;

    public StationSQLiteEntity(int url_id, String city, String country, String web_source, String type, double longitude, double latitude, double altitude, double cos_latitude, double sin_latitude, double cos_longitude, double sin_longitude) {
        this.url_id = url_id;
        this.city = city;
        this.country = country;
        this.web_source = web_source;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.cos_latitude = cos_latitude;
        this.sin_latitude = sin_latitude;
        this.cos_longitude = cos_longitude;
        this.sin_longitude = sin_longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrl_id() {
        return url_id;
    }

    public void setUrl_id(int url_id) {
        this.url_id = url_id;
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

    public String getWeb_source() {
        return web_source;
    }

    public void setWeb_source(String web_source) {
        this.web_source = web_source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getCos_latitude() {
        return cos_latitude;
    }

    public void setCos_latitude(double cos_latitude) {
        this.cos_latitude = cos_latitude;
    }

    public double getSin_latitude() {
        return sin_latitude;
    }

    public void setSin_latitude(double sin_latitude) {
        this.sin_latitude = sin_latitude;
    }

    public double getCos_longitude() {
        return cos_longitude;
    }

    public void setCos_longitude(double cos_longitude) {
        this.cos_longitude = cos_longitude;
    }

    public double getSin_longitude() {
        return sin_longitude;
    }

    public void setSin_longitude(double sin_longitude) {
        this.sin_longitude = sin_longitude;
    }

    @Override
    public String toString() {
        return "StationSQLiteEntity{" +
                "id=" + id +
                ", url_id=" + url_id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", web_source='" + web_source + '\'' +
                ", type='" + type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                '}';
    }
}
