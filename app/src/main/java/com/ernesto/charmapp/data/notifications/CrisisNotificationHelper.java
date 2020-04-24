package com.ernesto.charmapp.data.notifications;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.sharedPreferences.SharedPreferencesManager;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientMainActivity;
import com.ernesto.charmapp.presentation.activities.splashScreenActivities.SplashScreenActivity;

public class CrisisNotificationHelper extends ContextWrapper {

    private static String CRISIS_CHANNEL_ID = "CrisisChannelId";

    private static String CRISIS_CHANNEL_NAME = "CrisisChannel";

    private NotificationManager notificationManager;

    public CrisisNotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createCrisisChannel();
        }
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createCrisisChannel() {
        NotificationChannel crisisChannel = new NotificationChannel(CRISIS_CHANNEL_ID, CRISIS_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

        crisisChannel.setVibrationPattern(new long[]{500, 500});
        crisisChannel.enableVibration(true);
        crisisChannel.enableLights(true);

        getNotificationManager().createNotificationChannel(crisisChannel);
    }

    public NotificationCompat.Builder getCrisisChannelNotification() {
        Intent launchOnNotificationClicked = new Intent(
                this,
                SharedPreferencesManager.getInstance(this).userLoggedIn() ? PatientMainActivity.class : SplashScreenActivity.class
        );

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchOnNotificationClicked, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), CRISIS_CHANNEL_ID)
                .setContentTitle("CharmApp: Recordatorio de crisis")
                .setContentText("Tienes una crisis en curso, recuerda finalizarla si ya ha acabado")
                .setSmallIcon(R.drawable.ic_iconocharmapp)
                .setVibrate(new long[]{500, 500})
                .setLights(Color.WHITE, 1000, 1000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
    }

}
