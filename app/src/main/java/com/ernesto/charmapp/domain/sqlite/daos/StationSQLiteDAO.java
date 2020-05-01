package com.ernesto.charmapp.domain.sqlite.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;

import java.util.List;

@Dao
public interface StationSQLiteDAO {

    public static final double RAD2DEG = Math.PI / 180;

    public static final double RADIUS = 6371;

    double MAX_DISTANCE = Math.cos(5.0 / RADIUS);

    @Insert
    void createStation(StationSQLiteEntity station);

    @Query("SELECT * FROM station_table ORDER BY id ASC")
    LiveData<List<StationSQLiteEntity>> readAllStations();

    @Query("SELECT * FROM station_table ORDER BY id ASC")
    List<StationSQLiteEntity> readAllStationsList();

    @Query("SELECT * FROM station_table WHERE :sin_lat * sin_latitude + :cos_lat * cos_latitude *(cos_longitude * :cos_long + sin_longitude * :sin_long) < :max_distance AND type = 'meteo'")
    List<StationSQLiteEntity> readMeteoStations(double sin_lat, double cos_lat, double cos_long, double sin_long, double max_distance);

    @Query("SELECT station_table.city FROM station_table  WHERE (:cos_lat * station_table.cos_latitude * (station_table.cos_longitude * :cos_long + station_table.sin_longitude * :sin_long) +  :sin_lat * station_table.sin_latitude) <= :max_distance AND station_table.type = :type")
    List<String> readPollutionStations(double sin_lat, double cos_lat, double cos_long, double sin_long, double max_distance, String type);

    @Update
    void updateStation(StationSQLiteEntity station);

    @Delete
    void deleteStation(StationSQLiteEntity station);

    //void populateDatabase();
}
