package com.ernesto.charmapp.data.sqlite;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.domain.retrofitEntities.StationRetrofit;
import com.ernesto.charmapp.domain.sqlite.daos.ForecastDataSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.daos.StationSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.entities.ForecastDataSQLiteEntity;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;
import com.ernesto.charmapp.interactors.responses.stationResponses.ReadAllStationsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@Database(entities = {StationSQLiteEntity.class, ForecastDataSQLiteEntity.class}, version = 14)
public abstract class StationDatabase extends RoomDatabase {

    private static StationDatabase instance;

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            System.out.println("LLAMADA AL ONCREATE");
            new LoadStationsAT(instance).execute();
        }

       /*+@Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            System.out.println("LLAMADA AL ONOPEN");
            new LoadStationsAT(instance).execute();
        }*/
    };

    public static synchronized StationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), StationDatabase.class, "station_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            //No tienes que hacer nada más, simplemente cuando vayas a hacer una query (y la bd no se haya creado antes, se ejecutará el callback y cargará esas tablas okey, gracias
            instance.query("SELECT 1", null);
        }
        return instance;
    }

    public abstract StationSQLiteDAO stationDAO();

    public abstract ForecastDataSQLiteDAO forecastDataDAO();

    private static class LoadStationsAT extends AsyncTask<Void, Void, Void> {

        private final StationSQLiteDAO stationDAO;
        private List<StationRetrofit> allStations;

        public LoadStationsAT(StationDatabase db) {
            this.stationDAO = db.stationDAO();
            allStations = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("DOINBACKGROUND DEL STATION DATABASEEEEE");
            Call<ReadAllStationsResponse> readAllStations = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .readAllStations();
            try {
                Response<ReadAllStationsResponse> response = readAllStations.execute();
                if (response.isSuccessful()) { //Ahora si, no?
                    ReadAllStationsResponse stationsResponse = response.body();
                    List<StationRetrofit> stations = stationsResponse.getStations();
                    for (int i = 0; i < stations.size(); i++) {
                        StationRetrofit s = stations.get(i);
                        System.out.println("Station " + i + " of " + stations.size());
                        allStations.add(s);
                        StationSQLiteEntity station = new StationSQLiteEntity(Integer.parseInt(s.getStationUrlId()), s.getCity(), s.getCountry(), s.getWebSource(), s.getType(), Double.parseDouble(s.getLongitude()), Double.parseDouble(s.getLat()), 0
                                , 0, 0, 0, 0);
                        station.setId(Integer.parseInt(s.getStationId()));
                        station.setCos_latitude(Math.cos(station.getLatitude() * Math.PI / 180));
                        station.setSin_latitude(Math.sin(station.getLatitude() * Math.PI / 180));
                        station.setSin_longitude(Math.cos(station.getLongitude() * Math.PI / 180));
                        station.setCos_longitude(Math.sin(station.getLongitude() * Math.PI / 180));
                        stationDAO.createStation(station);
                    }
                } else {
                    Log.d("Llenar BD", "Error al llenar la base de datos");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
