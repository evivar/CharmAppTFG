package com.ernesto.charmapp.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase RetrofitClient
 * <p>
 * Clase con la instancia a Retrofit2 para poder hacer las llamadas a la API
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 */
public class RetrofitClient {

    /**
     * URL base para las llamadas a la API
     */
    private static final String BASE_URL_AWS = "http://34.254.100.223/slimTest/public/";

    /**
     * Instancia de la clase RetrofitClient
     */
    private static RetrofitClient instance;

    /**
     * Objeto Retrofit
     */
    private Retrofit retrofit;

    /**
     * Constructor por defecto
     */
    private RetrofitClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_AWS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Método que devuelve y crea en caso de que no exista, una instancia de la clase RetrofitClient
     *
     * @return Instancia RetrofitClient
     */
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    /**
     * Método que devuelve la interfaz IAPICalls
     *
     * @return IAPICalls
     * @see IAPICalls
     */
    public IAPICalls getAPI() {
        return retrofit.create(IAPICalls.class);
    }

}
