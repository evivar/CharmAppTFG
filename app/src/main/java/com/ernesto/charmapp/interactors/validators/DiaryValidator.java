package com.ernesto.charmapp.interactors.validators;

import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.interactors.responses.diaryResponses.DiaryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryValidator {

    private String wrongFields;

    private boolean wrongDate = false;

    public DiaryValidator() {
        this.wrongFields = "";
    }


    // Recorrer el objeto fields con un foreach
    // Dentro del bucle distinguir entre si es un string
    public boolean validate(String sleep_time, String diaryDate, String sport_time, String alcohol, String smoke, String feeling, String patient_id) {
        boolean valid = true;

        if ((sleep_time == null) || (sleep_time.isEmpty()) || (sleep_time.length() == 0) || (sleep_time.length() > 3) || (Integer.parseInt(sleep_time) >= 24)) {
            valid = false;
            wrongFields += "Tiempo de sueño\n";
        }
        existsDiaryForDate(patient_id, diaryDate);
        if (wrongDate) {
            valid = false;
            wrongFields += "Fecha no válida, el diario ya existe\n";
        }

        if((sport_time == null) || (sport_time.equals("")) || (sport_time.length() == 0)){
            valid = false;
            wrongFields += "Tiempo de ejercicio\n";
        }

        if((alcohol == null) || (alcohol.equals("")) || (alcohol.length() == 0)){
            valid = false;
            wrongFields += "Alcohol\n";
        }

        if((smoke == null) || (smoke.equals("")) || (smoke.length() == 0)){
            valid = false;
            wrongFields += "Cigarrillos fumados\n";
        }

        if((feeling == null) || (feeling.isEmpty()) || (feeling.length() == 0) || (feeling.length() > 255)){
            valid = false;
            wrongFields += "Sensaciones\n";
        }

        return valid;
    }

    public String getWrongFields(){
        return this.wrongFields;
    }

    private void existsDiaryForDate(String patient_id, String diaryDate) {
        Call<DiaryResponse> readDiaryByDate = RetrofitClient
                .getInstance()
                .getAPI()
                .readDiaryByDate(patient_id, diaryDate);
        readDiaryByDate.enqueue(new Callback<DiaryResponse>() {
            @Override
            public void onResponse(Call<DiaryResponse> call, Response<DiaryResponse> response) {
                DiaryResponse diaryResponse = response.body();
                if (!diaryResponse.getError()) {
                    wrongDate = true;
                }
            }

            @Override
            public void onFailure(Call<DiaryResponse> call, Throwable t) {

            }
        });
    }
}
