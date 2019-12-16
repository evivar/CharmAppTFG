package com.ernesto.charmapp.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://147.96.127.212/CharmAppAPI/public/";

    private static final String BASE_URL_PC = "http://192.168.0.20/CharmAppAPI/public/";

    private static final String BASE_URL_UCM = "http://147.96.114.153/charmAppAPI/public/";

    private static RetrofitClient instance;

    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_UCM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public APICalls getAPI(){
        return retrofit.create(APICalls.class);
    }

}
