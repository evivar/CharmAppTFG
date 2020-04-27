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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@Database(entities = {StationSQLiteEntity.class}, version = 11)
public abstract class StationDatabase extends RoomDatabase {

    private static StationDatabase instance;

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            System.out.println("ESTOY EN EL CALLBACK");
            Log.d("StationDatabase", "onCreate: CALLBACK");
            new LoadStationsAT(instance).execute();
        }
    };

    public static synchronized StationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), StationDatabase.class, "station_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            //No tienes que hacer nada más, simplemente cuando vayas a hacer una query (y la bd no se haya creado antes, se ejecutará el callback y cargará esas tablas okey, gracias
        }
        return instance;
    }

    public abstract StationSQLiteDAO stationDAO();

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
            readAllStations.enqueue(new retrofit2.Callback<ReadAllStationsResponse>() {
                @Override
                public void onResponse(Call<ReadAllStationsResponse> call, Response<ReadAllStationsResponse> response) {
                    ReadAllStationsResponse stationsResponse = response.body();
                    if (!stationsResponse.getError()) {
                        allStations = stationsResponse.getStations();
                    } else {
                        Log.d("Llenar BD", "Error al llenar la base de datos");
                    }
                }

                @Override
                public void onFailure(Call<ReadAllStationsResponse> call, Throwable t) {

                }
            });
            System.out.println("IMPRIMIENDO DESDE STATIONDATABASE.JAVA");
            for (StationRetrofit s : allStations) {
                StationSQLiteEntity station = new StationSQLiteEntity(Integer.parseInt(s.getStationUrlId()), s.getCity(), s.getCountry(), s.getWebSource(), s.getType(), Double.parseDouble(s.getLongitude()), Double.parseDouble(s.getLat()), 0);
                station.setId(Integer.parseInt(s.getStationId()));
                System.out.println(station.toString());
                //this.stationDAO.createStation(station);
            }
            return null;
        }
    }

}
