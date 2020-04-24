package com.ernesto.charmapp.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.location.LocationService;
import com.ernesto.charmapp.presentation.activities.doctorActivities.DoctorLoginActivity;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientLoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button patientBtn;

    private Button doctorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.patientBtn = findViewById(R.id.patientBtn_main);
        this.patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PatientLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        this.doctorBtn = findViewById(R.id.doctorBtn_main);
        this.doctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoctorLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    public void startService(View v) {
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, LocationService.class);
        stopService(serviceIntent);
    }

}

