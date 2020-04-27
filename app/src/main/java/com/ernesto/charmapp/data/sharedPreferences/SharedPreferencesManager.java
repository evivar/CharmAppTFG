package com.ernesto.charmapp.data.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ernesto.charmapp.domain.retrofitEntities.Doctor;
import com.ernesto.charmapp.domain.retrofitEntities.Patient;

import java.util.Map;

/**
 * Clase SharedPreferencesManager
 * <p>
 * Clase donde se almacena la informacion de varias entidades para agilizar la aplicación reduciendo las llamadas a la API
 *
 * @author Ernesto Vivar Laviña evivar@ucm.es
 * @see <a href="https://developer.android.com/reference/android/content/SharedPreferences"> Documentación de SharedPreferences en la pagina oficial de Android </a>
 */
public class SharedPreferencesManager {

    /**
     * Nombre de las preferencias
     */
    private static final String SHARED_PREFFERENCES_NAME = "my_shared_prefferences";

    /**
     * Instancia de la clase SharedPreferencesManager
     */
    private static SharedPreferencesManager instance;

    /**
     * Contexto de la aplicación
     *
     * @see Context
     */
    private Context context;

    /**
     * Constructor por defecto
     *
     * @param context Contexto de la aplicación
     */
    private SharedPreferencesManager(Context context) {
        this.context = context;
    }

    /**
     * Método que devuelve y crea en caso de que no exista, una instancia de la clase SharedPreferencesManager
     *
     * @param context Contexto de la aplicación
     * @return Instancia SharedPreferencesManager
     */
    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    /**
     * Método que dado un objeto Patient guarda sus atributos en el editor
     *
     * @param patient Paciente
     */
    public void savePatient(Patient patient) {
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

    /**
     * Método que dado un entero con la duracion en días de las crisis de un paciente guarda la media en días de la duración en el editor.
     * En caso de que durase 365 días se guarda como media de la duración 14 días
     *
     * @param crisisDuration Duración de las crisis del paciente
     */
    public void saveCrisisDuration(int crisisDuration) {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferencesManager.edit();
        int duration = (crisisDuration == 365) ? 14 : crisisDuration / 2;
        editor.putInt("crisisDuration", duration);

        editor.apply();
    }

    public void saveDoctor(Doctor doctor) {
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

    /**
     * Método que guarda un booleano cuando se completa el tutorial OnBoarding
     *
     * @param completed
     */
    public void completeOnBoarding(boolean completed) {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesManager.edit();
        editor.putBoolean("OnBoardingCompleted", completed);
        editor.apply();
    }

    public void populateStations(boolean populated) {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesManager.edit();
        editor.putBoolean("DBPopulated", populated);
        editor.apply();
    }

    /**
     * Método que comprueba si el usuario esta logeado o no
     *
     * @return <ul>
     * <li> True si el usuario esta logeado</li>
     * <li> False en caso contrario</li>
     * </ul>
     */
    public boolean userLoggedIn() {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);

        return sharedPreferencesManager.getString("id", null) != null;
    }

    /**
     * Método que devuleve un objeto Patient con los atributos guardados en el editor
     *
     * @return Paciente del editor
     */
    public Patient getPatient() {
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

    public Doctor getDoctor() {
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

    public boolean isOnBoardingCompleted() {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferencesManager.getBoolean("OnBoardingCompleted", false);
    }

    public boolean isDBPopulate() {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferencesManager.getBoolean("DBPopulated", false);
    }

    /**
     * Método que limpia el editor de SharedPreferences cuando se cierra la sesión, excepto el campo 'OnBoardingCompleted' que indica si se ha finalizado el tutorial OnBoarding de la aplicación
     */
    public void logOut() {
        SharedPreferences sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesManager.edit();
        Map<String, ?> preferences = sharedPreferencesManager.getAll();
        for (Map.Entry<String, ?> preference : preferences.entrySet()) {
            if (!preference.getKey().equals("OnBoardingCompleted")) {
                editor.remove(preference.getKey());
            }
        }
        editor.apply();
    }


}
