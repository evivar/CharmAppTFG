package com.ernesto.charmapp.domain.sqlite.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "forecastData_table", primaryKeys = {"patient_id", "timestamp"})

public class ForecastDataSQLiteEntity {

    @ColumnInfo
    @NonNull
    private int patient_id;

    private double avg_temp;

    private double avg_pressure;

    private double avg_rainfall;

    @ColumnInfo
    @NonNull
    private String timestamp;


    public ForecastDataSQLiteEntity(double avg_temp, double avg_pressure, double avg_rainfall) {
        this.avg_temp = avg_temp;
        this.avg_pressure = avg_pressure;
        this.avg_rainfall = avg_rainfall;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getAvg_temp() {
        return avg_temp;
    }

    public void setAvg_temp(double avg_temp) {
        this.avg_temp = avg_temp;
    }

    public double getAvg_pressure() {
        return avg_pressure;
    }

    public void setAvg_pressure(double avg_pressure) {
        this.avg_pressure = avg_pressure;
    }

    public double getAvg_rainfall() {
        return avg_rainfall;
    }

    public void setAvg_rainfall(double avg_rainfall) {
        this.avg_rainfall = avg_rainfall;
    }
}
