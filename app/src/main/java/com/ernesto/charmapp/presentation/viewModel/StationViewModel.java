package com.ernesto.charmapp.presentation.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ernesto.charmapp.data.sqlite.StationRepository;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;

import java.util.List;

public class StationViewModel extends AndroidViewModel {

    private StationRepository repository;

    private LiveData<List<StationSQLiteEntity>> allStations;

    public StationViewModel(@NonNull Application application) {
        super(application);
        repository = new StationRepository(application);
        allStations = repository.readAllStations();
    }

    public void createStation(StationSQLiteEntity station) {
        repository.createStation(station);
    }

    public LiveData<List<StationSQLiteEntity>> readAllStations() {
        return allStations;
    }

    public void updateStation(StationSQLiteEntity station) {
        repository.updateStation(station);
    }

    public void deleteStation(StationSQLiteEntity station) {
        repository.deleteStation(station);
    }
}
