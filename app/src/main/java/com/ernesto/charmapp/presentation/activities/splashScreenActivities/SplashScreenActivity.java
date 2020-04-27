package com.ernesto.charmapp.presentation.activities.splashScreenActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.sqlite.StationDatabase;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientLoginActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StationDatabase.getInstance(this);
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
    }
}
