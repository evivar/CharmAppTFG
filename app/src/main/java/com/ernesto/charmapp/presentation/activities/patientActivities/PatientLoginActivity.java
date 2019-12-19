package com.ernesto.charmapp.presentation.activities.patientActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.data.SharedPreferencesManager;
import com.ernesto.charmapp.interactors.responses.PatientLoginResponse;
import com.ernesto.charmapp.interactors.validators.LoginValidator;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientLoginActivity extends AppCompatActivity {

    private LoginValidator validator;

    private EditText emailTxt;

    private String email;

    private EditText passwordTxt;

    private String password;

    private Button loginBtn;

    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        registerBtn = findViewById(R.id.registerBtn_patientLogin);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void login() throws NoSuchAlgorithmException {
        // Ver como hacer una interfaz para todos los validadores
        email = emailTxt.getText().toString();
        password = passwordTxt.getText().toString();
        if (validator.validate(email, password)) {
            /* TODO: Ya hashea la contraseña con SHA512, solo falta almacenarla así en la BBDD */
            System.out.println(SHA512(password));
            Call<PatientLoginResponse> loginPatient = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .patientLogin(this.emailTxt.getText().toString(), this.passwordTxt.getText().toString());
            loginPatient.enqueue(new Callback<PatientLoginResponse>() {
                @Override
                public void onResponse(Call<PatientLoginResponse> call, Response<PatientLoginResponse> response) {
                    System.out.println("Entro en el request");
                    PatientLoginResponse patientLoginResponse = response.body();
                    if (!patientLoginResponse.getEstadoDelError()) {
                        System.out.println("CAMBIA DE PANTALLA CARAPENE");
                        SharedPreferencesManager.getInstance(PatientLoginActivity.this).savePatient(patientLoginResponse.getPaciente());
                        Intent intent = new Intent(PatientLoginActivity.this, PatientMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PatientLoginActivity.this, patientLoginResponse.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PatientLoginResponse> call, Throwable t) {

                }
            });
        }
    }

    private String SHA512(String password) throws NoSuchAlgorithmException {
        String hashedPassword = "";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        hashedPassword = String.format("%064x", new BigInteger(1, digest));
        System.out.println(hashedPassword);

        return hashedPassword;
    }

    public void register() {
        // Ver que hace este boton -> Quiza seria bueno que rellenase los daatos personales y se le enviara un email al medico para que pudiera darle de alta o algo asi
    }

}
