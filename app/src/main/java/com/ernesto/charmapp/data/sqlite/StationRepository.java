package com.ernesto.charmapp.data.sqlite;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ernesto.charmapp.domain.sqlite.daos.StationSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;

import java.util.ArrayList;
import java.util.List;

public class StationRepository {

    private StationSQLiteDAO stationDAO;

    private LiveData<List<StationSQLiteEntity>> allStations;

    public StationRepository(Context context) {
        StationDatabase database = StationDatabase.getInstance(context);
        stationDAO = database.stationDAO();
        allStations = stationDAO.readAllStations();
    }

    public void createStation(StationSQLiteEntity station) {
        new CreateStationAT(stationDAO).execute(station);
    }

    public LiveData<List<StationSQLiteEntity>> readAllStations() {
        return allStations;
    }

    public List<StationSQLiteEntity> readNearStations(double latitude, double longitude) {
        List<StationSQLiteEntity> stations = (List<StationSQLiteEntity>) allStations;
        List<StationSQLiteEntity> stations5km = new ArrayList<>();
        for (StationSQLiteEntity station : stations) {
            if (isInRadius(station, latitude, longitude)) {
                stations5km.add(station);
            }
        }
        return stations5km;
    }

    private boolean isInRadius(StationSQLiteEntity station, double latitude, double longitude) {
        return Math.acos(Math.sin(station.getLatitude() * 0.0175) * Math.sin(longitude * 0.0175) + Math.cos(station.getLatitude() * 0.0175) * Math.cos(longitude * 0.0175) *
                Math.cos((latitude * 0.0175) - (longitude * 0.0175))) * 6371 <= 5;
    }

    public void updateStation(StationSQLiteEntity station) {
        new UpdateStationAT(stationDAO).execute(station);
    }

    public void deleteStation(StationSQLiteEntity station) {
        new DeleteStationAT(stationDAO).execute(station);
    }

    private static class CreateStationAT extends AsyncTask<StationSQLiteEntity, Void, Void> {

        private StationSQLiteDAO stationDAO;

        public CreateStationAT(StationSQLiteDAO stationDAO) {
            this.stationDAO = stationDAO;
        }

        @Override
        protected Void doInBackground(StationSQLiteEntity... stationSQLiteEntities) {
            stationDAO.createStation(stationSQLiteEntities[0]);
            return null;
        }
    }

    private static class UpdateStationAT extends AsyncTask<StationSQLiteEntity, Void, Void> {

        private StationSQLiteDAO stationDAO;

        public UpdateStationAT(StationSQLiteDAO stationDAO) {
            this.stationDAO = stationDAO;
        }

        @Override
        protected Void doInBackground(StationSQLiteEntity... stationSQLiteEntities) {
            stationDAO.updateStation(stationSQLiteEntities[0]);
            return null;
        }
    }

    private static class DeleteStationAT extends AsyncTask<StationSQLiteEntity, Void, Void> {

        private StationSQLiteDAO stationDAO;

        public DeleteStationAT(StationSQLiteDAO stationDAO) {
            this.stationDAO = stationDAO;
        }

        @Override
        protected Void doInBackground(StationSQLiteEntity... stationSQLiteEntities) {
            stationDAO.deleteStation(stationSQLiteEntities[0]);
            return null;
        }
    }
}
