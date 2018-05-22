package com.example.tayvigilator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    Vibrator v;
    Context ct;
    String  title;

    @Override
    public void onReceive(Context context, Intent intent) {
        ct=context;

        v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);
        Toast.makeText(context, "Next exam is 1 hour away", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        try{
            title = intent.getExtras().get("title").toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        Notification(context, "1 hour to exam start.");

    }


    public void Notification(Context context, String message) {
        String strtitle = "Scheduled Invigilation Reminder";
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Scheduled Invigilation Reminder", title);
        intent.putExtra("1 hour to exam start", title);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                .setSmallIcon(R.drawable.logo)
                .setTicker(message)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(38, builder.build());

    }
}
