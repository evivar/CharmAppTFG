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
import com.ernesto.charmapp.domain.sqlite.daos.StationSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;
import com.ernesto.charmapp.interactors.responses.stationResponses.ReadAllStationsResponse;

import retrofit2.Call;
import retrofit2.Response;

@Database(entities = {StationSQLiteEntity.class}, version = 3)
public abstract class StationDatabase extends RoomDatabase {

    private static StationDatabase instance;

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new LoadStationsAT(instance).execute();
        }
    };

    public static synchronized StationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), StationDatabase.class, "station_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract StationSQLiteDAO stationDAO();

    private static class LoadStationsAT extends AsyncTask<Void, Void, Void> {

        private StationSQLiteDAO stationDAO;


        public LoadStationsAT(StationDatabase db) {
            this.stationDAO = db.stationDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Test 1: Meto una station a pelo
            StationSQLiteEntity station = new StationSQLiteEntity(123, "Kirigistan", "ES", "ERNES", "meteo", 488.0, -3.75, 0);
            stationDAO.createStation(station);
            // Test 2: Llamo al API y cojo todas las estaciones y las meto todas TODO
            // Vamos por partes: Primero llamo al API e imprimo por pantalla toooodas las estaciones y luego las meto
            Call<ReadAllStationsResponse> readAllStations = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .readAllStations();
            readAllStations.enqueue(new retrofit2.Callback<ReadAllStationsResponse>() {
                @Override
                public void onResponse(Call<ReadAllStationsResponse> call, Response<ReadAllStationsResponse> response) {
                    ReadAllStationsResponse stationsResponse = response.body();
                    if (!stationsResponse.getError()) {
                        for (StationRetrofit s : stationsResponse.getStations()) {
                            System.out.println(s);
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
            return null;
        }
    }

}
