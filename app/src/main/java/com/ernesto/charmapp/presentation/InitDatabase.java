package com.ernesto.charmapp.presentation;

import android.app.Application;

import com.ernesto.charmapp.data.sqlite.StationDatabase;
import com.ernesto.charmapp.data.sqlite.StationRepository;

public class InitDatabase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StationDatabase.getInstance(this);
        StationRepository repository = new StationRepository(this);
        repository.populateDatabase();
    }
}
