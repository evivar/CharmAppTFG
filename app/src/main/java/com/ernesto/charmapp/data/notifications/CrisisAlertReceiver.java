package com.ernesto.charmapp.data.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class CrisisAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CrisisNotificationHelper crisisNotificationHelper = new CrisisNotificationHelper(context);
        NotificationCompat.Builder nb = crisisNotificationHelper.getCrisisChannelNotification();
        crisisNotificationHelper.getNotificationManager().notify(2, nb.build());
    }
}
