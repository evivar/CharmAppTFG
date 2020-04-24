package com.ernesto.charmapp.data.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class LocationService extends Service {

    private LocationAlertReceiver locationAlertReceiver;

    public void onCreate() {
        super.onCreate();
        if (locationAlertReceiver == null) {
            locationAlertReceiver = new LocationAlertReceiver();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationAlertReceiver.setAlarm(LocationService.this);
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
