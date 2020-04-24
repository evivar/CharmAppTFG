package com.ernesto.charmapp.data.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class DiaryAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DiaryNotificationHelper diaryNotificationHelper = new DiaryNotificationHelper(context);
        NotificationCompat.Builder nb = diaryNotificationHelper.getDiaryChannelNotification();
        diaryNotificationHelper.getNotificationManager().notify(1, nb.build());
    }
}
