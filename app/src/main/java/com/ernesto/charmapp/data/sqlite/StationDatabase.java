package com.ernesto.charmapp.data.sqlite;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ernesto.charmapp.domain.sqlite.daos.StationSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;

@Database(entities = {StationSQLiteEntity.class}, version = 2)
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
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new LoadStationsAT(instance).execute();
                        }
                    })
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
            System.out.println("HOOLAAAA STATIONDB DOINBACKGROUND");
            StationSQLiteEntity station = new StationSQLiteEntity(123, "Kirigistan", "ES", "ERNES", "meteo", 488.0, -3.75, 0);
            stationDAO.createStation(station);
            // Test 2: Llamo al API y cojo todas las estaciones y las meto todas TODO
            // Vamos por partes: Primero llamo al API e imprimo por pantalla toooodas las estaciones
            return null;
        }
    }

}
