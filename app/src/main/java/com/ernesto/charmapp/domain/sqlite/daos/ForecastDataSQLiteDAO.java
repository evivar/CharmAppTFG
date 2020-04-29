package com.ernesto.charmapp.domain.sqlite.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ernesto.charmapp.domain.sqlite.entities.ForecastDataSQLiteEntity;

import java.util.List;

@Dao
public interface ForecastDataSQLiteDAO {

    @Insert
    void createForecastData(ForecastDataSQLiteEntity forecastData);

    @Query("SELECT * FROM forecastData_table")
    List<ForecastDataSQLiteEntity> readAll();

    @Query("SELECT * FROM forecastData_table WHERE patient_id = :patient_id")
    List<ForecastDataSQLiteEntity> readAllDataById(int patient_id);

    @Query("SELECT * FROM forecastData_table WHERE timestamp = :timestamp")
    List<ForecastDataSQLiteEntity> readAllDataByDate(String timestamp);

    @Query("SELECT * FROM forecastData_table WHERE patient_id = :patient_id AND timestamp = :timestamp")
    ForecastDataSQLiteEntity readLastDataById(int patient_id, String timestamp);

    @Query("SELECT * FROM forecastData_table WHERE patient_id = :patient_id LIMIT 48")
    List<ForecastDataSQLiteEntity> readLast48DataById(int patient_id);

    @Update
    void updateForecastData(ForecastDataSQLiteEntity forecastData);

    @Delete
    void deleteForecastData(ForecastDataSQLiteEntity forecastData);

}
