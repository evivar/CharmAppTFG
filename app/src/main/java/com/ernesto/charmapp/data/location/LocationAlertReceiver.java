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

import com.ernesto.charmapp.data.sqlite.ForecastDataRepository;
import com.ernesto.charmapp.data.sqlite.StationRepository;
import com.ernesto.charmapp.domain.sqlite.entities.StationSQLiteEntity;
import com.ernesto.charmapp.presentation.viewModel.StationViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        double latGPS = 0;
        double longGPS = 0;
        double latNTW = 0;
        double longNTW = 0;
        String locNTW = "";
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "No has dado los permisos necesarios", Toast.LENGTH_SHORT).show();
        } else {
            Location gps;
            Location ntw;
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (gps != null) {
                    locGPS = "Latitud: " + String.valueOf(gps.getLatitude()) + "Longitud: " + String.valueOf(gps.getLongitude());
                    latGPS = gps.getLatitude();
                    longGPS = gps.getLongitude();
                    Log.d(TAG, "onReceive: Latitud: " + latGPS + " Longitud: " + longGPS);
                }
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                ntw = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (ntw != null) {
                    locNTW = "Latitud: " + String.valueOf(ntw.getLatitude()) + "Longitud: " + String.valueOf(ntw.getLongitude());
                    latNTW = ntw.getLatitude();
                    longNTW = ntw.getLongitude();
                    Log.d(TAG, "onReceive: Latitud: " + latNTW + " Longitud: " + longNTW);
                }
            }


            try {
                List<StationSQLiteEntity> meteoStations = new ReadNearStationsAT(latNTW, longNTW).execute(context).get();
                System.out.println(meteoStations.size());
                for (StationSQLiteEntity s : meteoStations) {
                    System.out.println(s.toString());
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

    private static class ReadNearStationsAT extends AsyncTask<Context, Void, List<StationSQLiteEntity>> {

        private double latitude;

        private double longitude;

        private double loc_cos_lat;

        private double loc_sin_lat;

        private double loc_cos_long;

        private double loc_sin_long;

        public ReadNearStationsAT(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.loc_cos_lat = Math.cos((latitude * Math.PI) / 180);
            this.loc_sin_lat = Math.sin((latitude * Math.PI) / 180);
            this.loc_cos_long = Math.cos((longitude * Math.PI) / 180);
            this.loc_sin_long = Math.sin((longitude * Math.PI) / 180);
            this.calcCos();
        }

        private void calcCos() {
            Log.d(TAG, "calcCos: " + loc_sin_lat + " " + loc_cos_lat + " " + loc_sin_long + " " + loc_cos_long);
        }

        @Override
        protected List<StationSQLiteEntity> doInBackground(Context... contexts) {
            StationRepository stationRepository = new StationRepository(contexts[0]);
            ForecastDataRepository forecastRepository = new ForecastDataRepository(contexts[0]);
            List<StationSQLiteEntity> meteoStations = stationRepository.readMeteoStations(latitude, longitude);
            return meteoStations;
        }
    }
}
