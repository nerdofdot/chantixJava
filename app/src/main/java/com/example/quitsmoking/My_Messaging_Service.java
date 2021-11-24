package com.example.quitsmoking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class My_Messaging_Service extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {

            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notification =
                    new NotificationCompat.Builder(this, CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_coin)
                            //icon file
                            .setContentTitle(title)
                            .setContentText(message)
                            .setColor(Color.RED)
                            //.setSound(defaultSoundUri)
                            .build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID,
                        "Limit Warning",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("This is for the limit of cigarettes you have set");
                notificationManager.createNotificationChannel(channel);
            }
        }

    }
}
