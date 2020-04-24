package com.ernesto.charmapp.data.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LocationAutoStart extends BroadcastReceiver {

    private LocationAlertReceiver locationAlertReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            locationAlertReceiver.setAlarm(context);
            Toast.makeText(context, "LocationService restarted", Toast.LENGTH_SHORT).show();
        }
    }
}
