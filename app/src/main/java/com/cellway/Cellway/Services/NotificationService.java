package com.cellway.Cellway.Services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.cellway.Cellway.Home;
import com.cellway.Cellway.Login;
import com.cellway.Cellway.NotificationActivity;
import com.cellway.Cellway.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;


public class NotificationService extends FirebaseMessagingService {

    private RemoteViews remoteViews;
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        SharedPreferences sharedPreferences = getSharedPreferences("Cellways", Context.MODE_PRIVATE);
//        String id = sharedPreferences.getString("userid","");

        //  Log.d("skalas", String.valueOf(remoteMessage.getData().get("title")));
          Log.d("notificationData", String.valueOf(remoteMessage.getData()));

//        remoteViews = new RemoteViews(getPackageName(),R.layout.row_custom_notification);
//        remoteViews.setTextViewText(R.id.tv_title,remoteMessage.getData().get("title"));
//        remoteViews.setTextViewText(R.id.tv_message,remoteMessage.getData().get("body"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            Random rand = new Random();
            int n = rand.nextInt(10000000);

            //  builder.setSound(alarmSound);

                Intent notificationIntent = new Intent(NotificationService.this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 1, notificationIntent, 0);




            int notifyID = n;
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = "abcd";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager mNotificationManager = (NotificationManager) NotificationService.this.getSystemService(NotificationService.this.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);

            Notification notification = new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.main_app_icon)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"))
                    .setAutoCancel(true)
//                     .setCustomBigContentView(remoteViews)
                    .setVibrate(new long[]{1000, 1000})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                    .setContentIntent(pendingIntent)
                    .build();
            mNotificationManager.notify(notifyID, notification);



        } else {

            Intent notificationIntent = new Intent(NotificationService.this, NotificationActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
            stackBuilder.addParentStack(NotificationActivity.class);
            stackBuilder.addNextIntent(notificationIntent);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this);

            Notification notification = builder
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"))
//                    .setCustomBigContentView(remoteViews)
                    .setSmallIcon(R.drawable.app_logo)
                    .setVibrate(new long[]{1000, 1000})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(remoteMessage.getData().get("body")))
                    .setContentIntent(pendingIntent).build();

            NotificationManager notificationManager = (NotificationManager) NotificationService.this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }

    }

}
