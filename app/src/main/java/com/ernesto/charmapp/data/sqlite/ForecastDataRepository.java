package com.ernesto.charmapp.data.sqlite;

import android.content.Context;
import android.os.AsyncTask;

import com.ernesto.charmapp.domain.sqlite.daos.ForecastDataSQLiteDAO;
import com.ernesto.charmapp.domain.sqlite.entities.ForecastDataSQLiteEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ForecastDataRepository {

    private ForecastDataSQLiteDAO forecastDataDAO;

    private List<ForecastDataSQLiteEntity> allForecastData;

    public ForecastDataRepository(Context context) {
        StationDatabase database = StationDatabase.getInstance(context);
        this.forecastDataDAO = database.forecastDataDAO();
        this.allForecastData = forecastDataDAO.readAll();
    }

    public void createForecastData(ForecastDataSQLiteEntity forecastData) {
        new CreateForecastDataAT(forecastDataDAO).execute(forecastData);
    }

    public List<ForecastDataSQLiteEntity> readAllForecastData() {
        return allForecastData;
    }

    public List<ForecastDataSQLiteEntity> readAllDataById(int patient_id) throws ExecutionException, InterruptedException {
        return new ReadAllForecastDataByIdAT(forecastDataDAO).execute(patient_id).get();
    }

    public List<ForecastDataSQLiteEntity> readAllDataByDate(String timestamp) throws ExecutionException, InterruptedException {
        return new ReadAllForecastDataByDateAT(forecastDataDAO).execute(timestamp).get();
    }

    public ForecastDataSQLiteEntity readLastDataById(int patient_id, String timestamp) throws ExecutionException, InterruptedException {
        return new ReadLastForecastDataByIdAT(forecastDataDAO, patient_id, timestamp).execute().get();
    }

    public List<ForecastDataSQLiteEntity> readLast48DataById(int patient_id) throws ExecutionException, InterruptedException {
        return new ReadLast48ForecastDataByDateAT(forecastDataDAO).execute(patient_id).get();
    }

    public void updateForecastData(ForecastDataSQLiteEntity forecastData) {
        new UpdateForecastDataAT(forecastDataDAO).execute(forecastData);
    }

    public void deleteForecastData(ForecastDataSQLiteEntity forecastData) {
        new DeleteForecastDataAT(forecastDataDAO).execute(forecastData);
    }

    private static class CreateForecastDataAT extends AsyncTask<ForecastDataSQLiteEntity, Void, Void> {

        private ForecastDataSQLiteDAO forecastDataDAO;

        public CreateForecastDataAT(ForecastDataSQLiteDAO forecastDataDAO) {
            this.forecastDataDAO = forecastDataDAO;
        }

        @Override
        protected Void doInBackground(ForecastDataSQLiteEntity... forecastDataSQLiteEntities) {
            forecastDataDAO.createForecastData(forecastDataSQLiteEntities[0]);
            return null;
        }
    }

    private static class ReadAllForecastDataByIdAT extends AsyncTask<Integer, Void, List<ForecastDataSQLiteEntity>> {

        private ForecastDataSQLiteDAO forecastDataDAO;

        public ReadAllForecastDataByIdAT(ForecastDataSQLiteDAO forecastDataDAO) {
            this.forecastDataDAO = forecastDataDAO;
        }

        @Override
        protected List<ForecastDataSQLiteEntity> doInBackground(Integer... integers) {
            return forecastDataDAO.readAllDataById(integers[0]);
        }
    }

    private static class ReadAllForecastDataByDateAT extends AsyncTask<String, Void, List<ForecastDataSQLiteEntity>> {

        private ForecastDataSQLiteDAO forecastDataDAO;

        public ReadAllForecastDataByDateAT(ForecastDataSQLiteDAO forecastDataDAO) {
            this.forecastDataDAO = forecastDataDAO;
        }

        @Override
        protected List<ForecastDataSQLiteEntity> doInBackground(String... strings) {
            return forecastDataDAO.readAllDataByDate(strings[0]);
        }
    }

    private static class ReadLastForecastDataByIdAT extends AsyncTask<Void, Void, ForecastDataSQLiteEntity> {

        private String timestamp;

        private int patient_id;

        private ForecastDataSQLiteDAO forecastDataDAO;

        public ReadLastForecastDataByIdAT(ForecastDataSQLiteDAO forecastDataDAO, int patient_id, String timestamp) {
            this.forecastDataDAO = forecastDataDAO;
            this.patient_id = patient_id;
            this.timestamp = timestamp;
        }

        @Override
        protected ForecastDataSQLiteEntity doInBackground(Void... voids) {
            return forecastDataDAO.readLastDataById(this.patient_id, this.timestamp);
        }
    }

    private static class ReadLast48ForecastDataByDateAT extends AsyncTask<Integer, Void, List<ForecastDataSQLiteEntity>> {

        private ForecastDataSQLiteDAO forecastDataDAO;

        public ReadLast48ForecastDataByDateAT(ForecastDataSQLiteDAO forecastDataDAO) {
            this.forecastDataDAO = forecastDataDAO;
        }

        @Override
        protected List<ForecastDataSQLiteEntity> doInBackground(Integer... integers) {
            return forecastDataDAO.readLast48DataById(integers[0]);
        }
    }

    private static class UpdateForecastDataAT extends AsyncTask<ForecastDataSQLiteEntity, Void, Void> {

        private ForecastDataSQLiteDAO forecastDataDAO;

        public UpdateForecastDataAT(ForecastDataSQLiteDAO forecastDataDAO) {
            this.forecastDataDAO = forecastDataDAO;
        }

        @Override
        protected Void doInBackground(ForecastDataSQLiteEntity... forecastDataSQLiteEntities) {
            forecastDataDAO.updateForecastData(forecastDataSQLiteEntities[0]);
            return null;
        }
    }

    private static class DeleteForecastDataAT extends AsyncTask<ForecastDataSQLiteEntity, Void, Void> {

        private ForecastDataSQLiteDAO forecastDataDAO;

        public DeleteForecastDataAT(ForecastDataSQLiteDAO forecastDataDAO) {
            this.forecastDataDAO = forecastDataDAO;
        }

        @Override
        protected Void doInBackground(ForecastDataSQLiteEntity... forecastDataSQLiteEntities) {
            forecastDataDAO.deleteForecastData(forecastDataSQLiteEntities[0]);
            return null;
        }
    }

}
