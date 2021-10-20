package com.example.covidselfreport;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


/**
 * A BroadcastReceiver specialized for this application.
 * Receives any alarms for this application.
 * When an alarm goes off, a notification is generated (to remind the user to take the daily intake survey) and delivered to the user.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    /**
     * This method receives an alarm broadcast and generates a notification reminding the user to take the daily intake survey.
     * @param context The context of the application
     * @param intent The intent used to go to this class:
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        //If the intent's action is a boot completed action (the phone just booted up), the AlarmBroadcastReceiver needs to be configured again.
        //Call MainActivity's startAlarmBroadcastReceiver() method to set up the daily notification alarm.
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            MainActivity.startAlarmBroadcastReceiver(context, MainActivity.getProfile().getNotificationHour(), MainActivity.getProfile().getNotificationMinute());
            return;
        }

        //Generate the necessary information and objects to send out the notification:
        String NOTIF_CHANNEL_ID = "Reminders";
        NotificationCompat.Builder builder; //Used to build the notification
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build the notification using the builder object, of type Builder:
        builder = new NotificationCompat.Builder(context, "M_CH_ID");
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Daily Intake Survey Reminder")
                .setContentText("Don't forget to take your daily intake survey today!")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Finish building the notification, using object builder (process differs depending on Android version):
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder.setChannelId(NOTIF_CHANNEL_ID);
        }
        else {
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        //Send out the notification:
        manager.notify(1, builder.build());
    }

}
