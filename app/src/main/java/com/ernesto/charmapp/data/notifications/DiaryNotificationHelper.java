package com.ernesto.charmapp.data.notifications;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.sharedPreferences.SharedPreferencesManager;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientMainActivity;
import com.ernesto.charmapp.presentation.activities.splashScreenActivities.SplashScreenActivity;

public class DiaryNotificationHelper extends ContextWrapper {

    private static String DIARY_CHANNEL_ID = "DiaryChannelId";

    private static String DIARY_CHANNEL_NAME = "DiaryChannel";

    private NotificationManager notificationManager;

    public DiaryNotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createDiaryChannel();
        }
    }

    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, DiaryAlertReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public static void disableDiaryNotifications(Context context) {
        ComponentName receiver = new ComponentName(context, DiaryAlertReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createDiaryChannel() {
        NotificationChannel diaryChannel = new NotificationChannel(DIARY_CHANNEL_ID, DIARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        diaryChannel.setVibrationPattern(new long[]{500, 500});
        diaryChannel.enableVibration(true);
        diaryChannel.enableLights(true);
        getNotificationManager().createNotificationChannel(diaryChannel);
    }

    public NotificationCompat.Builder getDiaryChannelNotification() {
        Intent launchOnNotificationClicked = new Intent(
                this,
                SharedPreferencesManager.getInstance(this).userLoggedIn() ? PatientMainActivity.class : SplashScreenActivity.class
        );

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchOnNotificationClicked, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), DIARY_CHANNEL_ID)
                .setContentTitle("CharmApp: Recordatorio de diario")
                .setContentText("No te olvides de rellenar el diario")
                .setSmallIcon(R.drawable.ic_iconocharmapp)
                .setVibrate(new long[]{500, 500})
                .setLights(Color.WHITE, 1000, 1000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
    }

}
