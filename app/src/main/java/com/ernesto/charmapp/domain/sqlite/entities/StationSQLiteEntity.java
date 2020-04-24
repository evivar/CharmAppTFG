package com.ernesto.charmapp.domain.sqlite.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "station_table")
public class StationSQLiteEntity {

    @PrimaryKey
    private int id;

    private int url_id;

    private String city;

    private String country;

    private String web_source;

    private String type;

    private double longitude;

    private double latitude;

    private double altitude;

    public StationSQLiteEntity(int url_id, String city, String country, String web_source, String type, double longitude, double latitude, double altitude) {
        this.url_id = url_id;
        this.city = city;
        this.country = country;
        this.web_source = web_source;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
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
}
