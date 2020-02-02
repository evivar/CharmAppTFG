package com.ernesto.charmapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.ernesto.charmapp.domain.Doctor;
import com.ernesto.charmapp.domain.Patient;

public class SharedPreferencesManager {

    private static final String SHARED_PREFFERENCES_NAME = "my_shared_prefferences";

    private static SharedPreferencesManager instance;

    private Context context;

    private SharedPreferencesManager(Context context){
        this.context = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferencesManager(context);
        }
        return  instance;
    }

    public void savePatient(Patient patient){
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesManager.edit();

        editor.putString("id", patient.getPatientId());
        editor.putString("email", patient.getEmail());
        editor.putString("password", patient.getPassword());
        editor.putString("name", patient.getName());
        editor.putString("surname1", patient.getSurname1());
        editor.putString("surname2", patient.getSurname2());
        editor.putString("init_date", patient.getInitDate());
        editor.putString("end_date", patient.getEndDate());
        editor.putInt("phone", patient.getPhone());
        int duration = (patient.getCh_duration() == 365) ? 14 : patient.getCh_duration() / 2;
        editor.putInt("ch_duration", duration);

        editor.apply();
    }

    public void saveCrisisDuration(int crisisDuration) {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesManager.edit();
        int duration = (crisisDuration == 365) ? 14 : crisisDuration / 2;
        editor.putInt("crisisDuration", duration);

        editor.apply();
    }

    public void saveDoctor(Doctor doctor){
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesManager.edit();

        editor.putString("id", doctor.getPatientId());
        editor.putString("email", doctor.getEmail());
        editor.putString("password", doctor.getPassword());
        editor.putString("name", doctor.getName());
        editor.putString("surname1", doctor.getSurname1());
        editor.putString("surname2", doctor.getSurname2());
        editor.putInt("phone", doctor.getPhone());

        editor.apply();
    }

    public boolean userLoggedIn(){
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);

        return sharedPreferencesManager.getString("id", null ) != null;
    }

    public Patient getPatient(){
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        Patient patient = new Patient(
                sharedPreferencesManager.getString("id", null),
                sharedPreferencesManager.getString("email", null),
                sharedPreferencesManager.getString("password", null),
                sharedPreferencesManager.getString("name", null),
                sharedPreferencesManager.getString("surname1", null),
                sharedPreferencesManager.getString("surname2", null),
                sharedPreferencesManager.getString("init_date", null),
                sharedPreferencesManager.getString("end_date", null),
                sharedPreferencesManager.getInt("phone", -1),
                sharedPreferencesManager.getInt("cc_duration", -1)

        );
        return patient;
    }

    public Doctor getDoctor(){
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        Doctor doctor = new Doctor(
                sharedPreferencesManager.getString("id", null),
                sharedPreferencesManager.getString("email", null),
                sharedPreferencesManager.getString("password", null),
                sharedPreferencesManager.getString("name", null),
                sharedPreferencesManager.getString("surname1", null),
                sharedPreferencesManager.getString("surname2", null),
                sharedPreferencesManager.getInt("phone", -1)
        );
        return doctor;
    }

    public void logOut(){
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesManager.edit();
        editor.clear();
        editor.apply();
    }

}
