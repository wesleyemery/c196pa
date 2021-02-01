package com.wgu_wemery.c196pa.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.wgu_wemery.c196pa.MainActivity;
import com.wgu_wemery.c196pa.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Receivers extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("RECEVIED","rever");

        String title = intent.getStringExtra("ALARM_TITLE");
        String description = intent.getStringExtra("ALARM_DESCRIPTION");

        Intent notificationIntent = new Intent(context ,  MainActivity. class ) ;
        notificationIntent.putExtra( "fromNotification" , true ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( context, 0 , notificationIntent , 0 ) ;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context , default_notification_channel_id ) ;
        mBuilder.setContentTitle( title) ;
        mBuilder.setContentIntent(pendingIntent) ;
        mBuilder.setContentText( description ) ;
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        mBuilder.setAutoCancel( true ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;


//        NotificationCompat.Builder myBuilder = new NotificationCompat.Builder(context);
//        myBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
//        myBuilder.setContentTitle(title);
//        myBuilder.setContentText(description);
//
//        Intent mainIntent = new Intent(context, MainActivity.class);
//        TaskStackBuilder stack = TaskStackBuilder.create(context);
//        stack.addParentStack(MainActivity.class);
//
//        stack.addNextIntent(mainIntent);
//        PendingIntent resultPendingIntent = stack.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//        myBuilder.setContentIntent(resultPendingIntent);
//
//        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//
//        mNotificationManager.notify(1, myBuilder.build());


    }
}
