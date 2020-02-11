package com.ernesto.charmapp.data;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.ernesto.charmapp.presentation.activities.MainActivity;
import com.ernesto.charmapp.presentation.activities.patientActivities.PatientMainActivity;

public class NotificationReceiver extends BroadcastReceiver {

    /**
     * Es lo que hace la notificacion cuando llega
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent broadcastIntent = new Intent(context, (SharedPreferencesManager.getInstance(context).getPatient().getPatientId() != null) ? PatientMainActivity.class : MainActivity.class);

        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationHelper notificationHelper = new NotificationHelper(context, "DChannel");
        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification();
        notificationBuilder.setContentIntent(actionIntent);
        notificationHelper.getManager().notify(1, notificationBuilder.build());

    }


}
   /* public void onReceive(Context ctx, Intent intent) {
        Bundle reicieveParams = intent.getExtras();
        int id = reicieveParams.getInt(AlarmLauncher.EXTRA_ALARMID);
        int msgType = reicieveParams.getInt(AlarmLauncher.EXTRA_TYPEID);
        Log.d(TAG, "ALARM_TYPE_ID: " + msgType);
        Log.d(TAG, "received alarm WAKEUP with id:" + id);

        //notification configuration
        pref = ctx.getApplicationContext().getSharedPreferences(
                Preferences.PREFS_FILE_NAME, Context.MODE_MULTI_PROCESS);
        boolean sound = pref.getBoolean(
                Preferences.KEY_SETTING_NOTIF_SOUND, Preferences.VALUE_ON);
        boolean vibrate = pref.getBoolean(
                Preferences.KEY_SETTING_NOTIF_VIBRATE, Preferences.VALUE_ON);
        String barMsg = "";
        switch (id) {
            case AlarmLauncher.ALARMID_INRESEARCH: //in research notif
                barMsg = "Gracias por su colaboración";
                break;
            case AlarmLauncher.ALARMID_SYMPTOMS:
                barMsg = "¿Cómo se encuentra hoy? ¿Ha tenido algún síntoma? Indíquenoslo";
                break;
            default:
                break;
        }
        if (!barMsg.isEmpty()) {
            //launch notification
            NotificationBuilder not = new NotificationBuilder(ctx);
            not.notifyToUser(barMsg, msgType, sound, vibrate);
        }
        Intent intent = new Intent(this, (SharedPreferencesManager.getInstance(PatientMainActivity.this).getPatient().getPatientId() != null) ? PatientMainActivity.class : MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Creamos un broadcastIntent
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        // Añadimos el name que habiamos puesto en la clase NotificationReceiver
        broadcastIntent.putExtra("toastMessage", "Ratatatata");
        // Hacemos otro pendingintent getBroadcast
        // FLAG_UPDATE_CURRENT -> Acutaliza el toastMessage puesto con putExtra()
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        // addAction(icono, titulo, pendingIntent) Añade un boton a la notificacion, se pueden poner hasta 3 de estos
        // setOnlyAlertOnce() es otro metodo
        // setColor(Color.COLOR) cambia el color
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Ch001")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("CharmApp")
                .setContentText("No te olvides de rellenar el diario")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());




    }        */