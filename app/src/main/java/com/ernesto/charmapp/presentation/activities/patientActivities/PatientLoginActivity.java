package com.ernesto.charmapp.presentation.activities.patientActivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.data.sharedPreferences.SharedPreferencesManager;
import com.ernesto.charmapp.interactors.hash.SHA512;
import com.ernesto.charmapp.interactors.responses.patientResponses.PatientResponse;
import com.ernesto.charmapp.interactors.validators.LoginValidator;
import com.ernesto.charmapp.presentation.activities.onBoardingActivities.OnBoardingActivity;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase PatientLoginActivity
 * <p>
 * Clase de la vista principal para el Login del paciente
 *
 * @author Ernesto Vivar Laviña, evivar@ucm.es
 */
public class PatientLoginActivity extends AppCompatActivity {

    // <editor-fold desc="Atributos">

    /**
     * Validador del login
     */
    private LoginValidator validator;

    /**
     * Campo de texto para el email
     */
    private EditText emailTxt;

    /**
     * Email del usuario
     */
    private String email;

    /**
     * Campo de texto para la contraseña
     */
    private EditText passwordTxt;

    /**
     * Contraseña del usuario
     */
    private String password;

    /**
     * Botón de login
     */
    private Button loginBtn;

    private Button registerBtn;

    // </editor-fold>

    // <editor-fold desc="Métodos protected">

    /**
     * Método sobre-escrito que inicializa la vista de la actividad
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_patient_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.emailTxt = findViewById(R.id.emailTxt_patientLogin);
        this.passwordTxt = findViewById(R.id.passwordTxt_patientLogin);
        this.validator = new LoginValidator();

        loginBtn = findViewById(R.id.logInBtn_patientLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // </editor-fold>

    // <editor-fold desc= "Métoodos públicos">

    /**
     * Método que hace comprueba la valided del email y la contraseña introducidos y logea al usuario en caso de que las credenciales sean correctas
     *
     * @throws NoSuchAlgorithmException Excepción lanzada si el algoritmo de hasheo SHA512 no estuviera disponible
     */
    public void login() throws NoSuchAlgorithmException {
        email = emailTxt.getText().toString();
        password = passwordTxt.getText().toString();
        if (validator.validate(email, password)) {
            Call<PatientResponse> patientLogin = RetrofitClient.getInstance().getAPI().login(email, SHA512.hashPassword(password));
            patientLogin.enqueue(new Callback<PatientResponse>() {
                @Override
                public void onResponse(Call<PatientResponse> call, Response<PatientResponse> response) {
                    PatientResponse patientResponse = response.body();
                    if (!patientResponse.getError()) {
                        SharedPreferencesManager.getInstance(PatientLoginActivity.this).savePatient(patientResponse.getPaciente());
                        if (SharedPreferencesManager.getInstance(PatientLoginActivity.this).isOnBoardingCompleted()) {
                            Intent intentPM = new Intent(PatientLoginActivity.this, PatientMainActivity.class);
                            intentPM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intentPM);
                        } else {
                            Intent intentOB = new Intent(PatientLoginActivity.this, OnBoardingActivity.class);
                            intentOB.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intentOB);
                        }
                    } else {
                        Toast.makeText(PatientLoginActivity.this, patientResponse.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PatientResponse> call, Throwable t) {
                    Toast.makeText(PatientLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // </editor-fold>

    // <editor-fold desc="Métodos privados">

    public void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET},
                    1);
        }
    }
    // </editor-fold>

}
