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
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationRepository {

    public static final double FIVE_KM = 5.0;

    public static final double DEG2RAD = 6371;

    private StationSQLiteDAO stationDAO;

    private LiveData<List<StationSQLiteEntity>> allStations;
    public static final double MAX_DISTANCE = Math.cos(FIVE_KM / DEG2RAD);
    private List<StationSQLiteEntity> allStationsList;

    public StationRepository(Context context) {
        StationDatabase database = StationDatabase.getInstance(context);
        stationDAO = database.stationDAO();
        allStations = stationDAO.readAllStations();
        allStationsList = stationDAO.readAllStationsList();
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

    public List<StationSQLiteEntity> readMeteoStations(double sin_lat, double cos_lat, double cos_long, double sin_long) throws ExecutionException, InterruptedException {
        return new ReadMeteoStationsAT(stationDAO).execute(sin_lat, cos_lat, cos_long, sin_long).get();
    }

    public List<StationSQLiteEntity> readPollutionStations(double sin_lat, double cos_lat, double cos_long, double sin_long) throws ExecutionException, InterruptedException {
        return new ReadPollutionStationsAT(stationDAO).execute(sin_lat, cos_lat, cos_long, sin_long).get();
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
            final List<StationRetrofit> stationsRetrofit = new ArrayList<>();
            final List<StationSQLiteEntity> stationsSQLite = new ArrayList<>();
            Call<ReadAllStationsResponse> readAllStations = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .readAllStations();
            readAllStations.enqueue(new Callback<ReadAllStationsResponse>() {
                @Override
                public void onResponse(Call<ReadAllStationsResponse> call, Response<ReadAllStationsResponse> response) {
                    ReadAllStationsResponse stationsResponse = response.body();
                    Log.d("ResponseBody", "onResponse: " + stationsResponse);
                    if (!stationsResponse.getError()) {
                        System.out.println("IMPRIMIENDO DESDE STATIONREPOSITORY.JAVA");
                        for (StationRetrofit s : stationsResponse.getStations()) {
                            StationSQLiteEntity station = new StationSQLiteEntity(Integer.parseInt(s.getStationUrlId()), s.getCity(), s.getCountry(), s.getWebSource(), s.getType(), Double.parseDouble(s.getLongitude()), Double.parseDouble(s.getLat()), 0
                                    , 0, 0, 0, 0);
                            station.setId(Integer.parseInt(s.getStationId()));
                            station.setCos_latitude(Math.cos(station.getLatitude() * Math.PI / 180));
                            station.setSin_latitude(Math.sin(station.getLatitude() * Math.PI / 180));
                            station.setSin_longitude(Math.cos(station.getLongitude() * Math.PI / 180));
                            station.setCos_longitude(Math.sin(station.getLongitude() * Math.PI / 180));
                            //stationDAO.createStation(station);
                            stationsRetrofit.add(s);
                            stationsSQLite.add(station);
                            //stationDAO.createStation(station);

                        }
                    } else {
                        Log.d("Llenar BD", "Error al llenar la base de datos");
                    }
                }

                @Override
                public void onFailure(Call<ReadAllStationsResponse> call, Throwable t) {

                }
            });
            Log.d("Tamaños", "doInBackground: " + stationsRetrofit.size() + " " + stationsSQLite.size());
            if (!stationsSQLite.isEmpty()) {
                System.out.println("VOY A METER A LA BASE DE DATOS");
                for (StationSQLiteEntity stationEntity : stationsSQLite) {
                    stationDAO.createStation(stationEntity);
                }
            }
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

    private static class ReadMeteoStationsAT extends AsyncTask<Double, Void, List<StationSQLiteEntity>> {
        private StationSQLiteDAO stationDAO;

        public ReadMeteoStationsAT(StationSQLiteDAO stationDAO) {
            this.stationDAO = stationDAO;
        }

        @Override
        protected List<StationSQLiteEntity> doInBackground(Double... coordinates) {
            return stationDAO.readMeteoStations(coordinates[0], coordinates[1], coordinates[2], coordinates[3], MAX_DISTANCE);
        }
    }

    private static class ReadPollutionStationsAT extends AsyncTask<Double, Void, List<StationSQLiteEntity>> {
        private StationSQLiteDAO stationDAO;

        public ReadPollutionStationsAT(StationSQLiteDAO stationDAO) {
            this.stationDAO = stationDAO;
        }

        @Override
        protected List<StationSQLiteEntity> doInBackground(Double... coordinates) {
            return stationDAO.readPollutionStations(coordinates[0], coordinates[1], coordinates[2], coordinates[3], MAX_DISTANCE);
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
