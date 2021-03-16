package com.example.covidselfreport;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            MainActivity.startAlarmBroadcastReceiver(context);
            return;
        }

        String NOTIF_CHANNEL_ID = "Reminders";
        NotificationCompat.Builder builder;
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(context, "M_CH_ID");
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Notification")
                .setContentText("Hello World!")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(NOTIF_CHANNEL_ID);
        }
        else {
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        manager.notify(1, builder.build());
    }

}
