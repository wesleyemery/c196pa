package com.wgu_wemery.c196pa.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.wgu_wemery.c196pa.MainActivity;

public class Receivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("ALARM_TITLE");
        String description = intent.getStringExtra("ALARM_DESCRIPTION");

        NotificationCompat.Builder myBuilder = new NotificationCompat.Builder(context);
        myBuilder.setContentTitle(title);
        myBuilder.setContentText(description);

        Intent mainIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stack = TaskStackBuilder.create(context);
        stack.addParentStack(MainActivity.class);

        stack.addNextIntent(mainIntent);
        PendingIntent resultPendingIntent = stack.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        myBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, myBuilder.build());


    }
}
