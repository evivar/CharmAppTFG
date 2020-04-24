package com.ernesto.charmapp.presentation.activities.splashScreenActivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientLoginActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen splashScreen = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(PatientLoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#1a1b29"))
                .withBeforeLogoText("CharmApp")
                .withLogo(R.drawable.ic_iconocharmapp_w);

        splashScreen.getBeforeLogoTextView().setTextColor(Color.WHITE);
        splashScreen.getBeforeLogoTextView().setTextSize(75);
        splashScreen.getBeforeLogoTextView().setTypeface(ResourcesCompat.getFont(this, R.font.muli));
        View splashScreenView = splashScreen.create();
        setContentView(splashScreenView);
        //requestPermissions();
    }

    public void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR},
                    1);
        }
    }
}
