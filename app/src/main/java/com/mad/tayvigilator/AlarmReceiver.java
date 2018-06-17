package com.mad.tayvigilator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    Vibrator v;
    Context ct;
    String  title;

    @Override
    public void onReceive(Context context, Intent intent) {
        ct=context;

        v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);

        Bundle bundle = intent.getExtras();
        try{
            title = intent.getExtras().get("title").toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        Notification(context, new Intent(context, Home.class));

    }


    public void Notification(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                .setSmallIcon(R.drawable.logo)
                .setTicker("Exam soon")
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Next exam will start in 1 hour")
                .setContentIntent(pIntent)
                .setAutoCancel(true);
        Notification n = builder.build();
        nm.notify(R.drawable.logo, n);

    }
}
