package com.ernesto.charmapp.presentation.activities.doctorActivities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.interactors.validators.LoginValidator;

public class DoctorLoginActivity extends AppCompatActivity {

    private LoginValidator validator;

    private EditText emailTxt;

    private String email;

    private EditText passwordTxt;

    private String password;

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.emailTxt = findViewById(R.id.emailTxt_doctorLogin);
        this.passwordTxt = findViewById(R.id.passwordTxt_doctorLogin);
        this.validator = new LoginValidator();

        loginBtn = findViewById(R.id.logInBtn_doctorLogin);
        /*loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
*/

    }

    /*public void login() {
        // Ver como hacer una interfaz para todos los validadores
        email = emailTxt.getText().toString();
        password = passwordTxt.getText().toString();
        if (validator.validate(email, password)) {
            Call<DoctorLoginResponse> loginDoctor = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .doctorLogin(this.emailTxt.getText().toString(), this.passwordTxt.getText().toString());
            loginDoctor.enqueue(new Callback<DoctorLoginResponse>() {
                @Override
                public void onResponse(Call<DoctorLoginResponse> call, Response<DoctorLoginResponse> response) {
                    DoctorLoginResponse doctorLoginResponse = response.body();
                    if (!doctorLoginResponse.getEstadoDelError()) {
                        SharedPreferencesManager.getInstance(DoctorLoginActivity.this).saveDoctor(doctorLoginResponse.getDoctor());
                        Intent intent = new Intent(DoctorLoginActivity.this, DoctorMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DoctorLoginActivity.this, doctorLoginResponse.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DoctorLoginResponse> call, Throwable t) {

                }
            });
        }
    }*/

}
