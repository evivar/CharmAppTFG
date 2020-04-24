package com.ernesto.charmapp.data.location;

/**
 * Localizacion en casa:
 * <p>
 * GoogleMaps: 40.4537248   -3.7121058
 * <p>
 * GPS:        40.45380351, -3.71002316
 * Network:    40.4539472,  -3.7100827
 */


import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.ernesto.charmapp.data.retrofit.RetrofitClient;
import com.ernesto.charmapp.data.sqlite.StationRepository;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;
import com.ernesto.charmapp.interactors.responses.crisisResponses.CrisisResponse;
import com.ernesto.charmapp.presentation.viewModel.StationViewModel;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationAlertReceiver extends BroadcastReceiver {

    private static final String TAG = "charmApp:LocationTag";

    private StationViewModel stationViewModel;

    public LocationAlertReceiver() {
    }

    public LocationAlertReceiver(StationViewModel stationViewModel) {
        this.stationViewModel = stationViewModel;
    }

    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, LocationAlertReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    //TODO: Las pilla de puta madre tronkii
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        // Poner el codigo
        String locGPS = "";
        String locNTW = "";
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(context, "No has dado los permisos necesarios", Toast.LENGTH_SHORT).show();

            //return;
        } else {
            Location gps;
            Location ntw;
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (gps != null) {
                    locGPS = "Latitud: " + String.valueOf(gps.getLatitude()) + "Longitud: " + String.valueOf(gps.getLongitude());
                }
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                ntw = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (ntw != null) {
                    locNTW = "Latitud: " + String.valueOf(ntw.getLatitude()) + "Longitud: " + String.valueOf(ntw.getLongitude());
                }
            }
            Call<CrisisResponse> testService = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .testService(Calendar.getInstance().getTime().toString(), (locGPS + " " + locNTW));
            testService.enqueue(new Callback<CrisisResponse>() {
                @Override
                public void onResponse(Call<CrisisResponse> call, Response<CrisisResponse> response) {
                    Log.d(TAG, "onResponse: Insercion correcta" + response.body().getMensaje());

                }

                @Override
                public void onFailure(Call<CrisisResponse> call, Throwable t) {

                }
            });


            Toast.makeText(context,
                    "GPS: " + locGPS + "\n"
                            + "NTW: " + locNTW + "\n",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, LocationAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar c = Calendar.getInstance();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 30, pendingIntent);
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, LocationAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private static class MeterUnRegistroAT extends AsyncTask<Context, Void, Void> {

        @Override
        protected Void doInBackground(Context... contexts) {
            StationRepository repository = new StationRepository(contexts[0]);
            StationSQLiteEntity station = new StationSQLiteEntity(123, "Madrid", "ES", "ERNES", "meteo", 40.0, -3.75, 0);
            station.setId(1);
            repository.createStation(station);
            return null;
        }
    }
}
