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

    @Insert
    void createStation(StationSQLiteEntity station);

    @Query("SELECT * FROM station_table ORDER BY id ASC")
    LiveData<List<StationSQLiteEntity>> readAllStations();

    @Update
    void updateStation(StationSQLiteEntity station);

    @Delete
    void deleteStation(StationSQLiteEntity station);

}
