package com.ernesto.charmapp.data.sqlite;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.domain.retrofitEntities.StationRetrofit;
import com.ernesto.charmapp.domain.sqlite.daos.StationSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;
import com.ernesto.charmapp.interactors.responses.stationResponses.ReadAllStationsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationRepository {

    private StationSQLiteDAO stationDAO;

    private LiveData<List<StationSQLiteEntity>> allStations;

    public StationRepository(Context context) {
        StationDatabase database = StationDatabase.getInstance(context);
        stationDAO = database.stationDAO();
        allStations = stationDAO.readAllStations();
    }

    public void populateDatabase() {
        new PopulateDataBaseAT(stationDAO).execute();
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

    private static class PopulateDataBaseAT extends AsyncTask<Void, Void, Void> {

        private StationSQLiteDAO stationDAO;

        public PopulateDataBaseAT(StationSQLiteDAO stationDAO) {
            this.stationDAO = stationDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Call<ReadAllStationsResponse> readAllStations = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .readAllStations();
            readAllStations.enqueue(new Callback<ReadAllStationsResponse>() {
                @Override
                public void onResponse(Call<ReadAllStationsResponse> call, Response<ReadAllStationsResponse> response) {
                    ReadAllStationsResponse stationsResponse = response.body();
                    if (!stationsResponse.getError()) {
                        for (StationRetrofit s : stationsResponse.getStations()) {
                            StationSQLiteEntity station = new StationSQLiteEntity(Integer.parseInt(s.getStationUrlId()), s.getCity(), s.getCountry(), s.getWebSource(), s.getType(), Double.parseDouble(s.getLongitude()), Double.parseDouble(s.getLat()), 0);
                            station.setId(Integer.parseInt(s.getStationId()));
                            stationDAO.createStation(station);
                        }
                    } else {
                        Log.d("Llenar BD", "Error al llenar la base de datos");
                    }
                }

                @Override
                public void onFailure(Call<ReadAllStationsResponse> call, Throwable t) {

                }
            });


            //StationSQLiteEntity station = new StationSQLiteEntity(412, "Madrid", "ES", "ACQUIN", "meteo", 40.0, -3.75, 0);
            //db.stationDAO().createStation(station);
            return null;
        }
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
