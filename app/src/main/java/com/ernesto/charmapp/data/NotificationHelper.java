package com.ernesto.charmapp.data;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.presentation.activities.MainActivity;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientMainActivity;

public class NotificationHelper extends ContextWrapper {

    public static final String DiaryNotificationChannelId = "DiaryCh";

    public static final String CrisisNotificationChannelId = "CrisisCh";

    public static final String DiaryNotificationChannelName = "Diary Channel";

    public static final String CrisisNotificationChannelName = "Crisis Channel";

    private String channelType;

    private NotificationManager notificationManager;

    public NotificationHelper(Context base, String channelType) {
        super(base);
        this.channelType = channelType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            switch (channelType) {
                case "DChannel":
                    createDiaryChannel();
                    break;
                case "CChannel":
                    createCrisisChannel();
                    break;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createDiaryChannel() {
        NotificationChannel diaryChannel = new NotificationChannel(DiaryNotificationChannelId, DiaryNotificationChannelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(diaryChannel);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createCrisisChannel() {
        NotificationChannel crisisChannel = new NotificationChannel(CrisisNotificationChannelId, CrisisNotificationChannelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(crisisChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        NotificationCompat.Builder nb = null;
        switch (this.channelType) {
            case "DChannel":
                nb = new NotificationCompat.Builder(getApplicationContext(), DiaryNotificationChannelId)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("CharmApp")
                        .setContentText("No te olvides de rellenar el diario");
                break;
            case "CChannel":
                nb = new NotificationCompat.Builder(getApplicationContext(), CrisisNotificationChannelId)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("CharmApp - Crisis")
                        .setContentText("Tienes una crisis activa");
                break;
        }

        Intent broadcastIntent = new Intent(this, (SharedPreferencesManager.getInstance(this).getPatient().getPatientId() != null) ? PatientMainActivity.class : MainActivity.class);

        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), DiaryNotificationChannelId)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("CharmApp")
                .setContentText("No te olvides de rellenar el diario")
                .setVibrate(new long[]{500, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(actionIntent);
    }

}


