package com.example.tatwa10;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "hospital_push_channel";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        int patientId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("patientId", -1);

        if (patientId != -1) {
            ApiService.saveFcmToken(patientId, token);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("FCM_MESSAGE", "Message received");

        String title = "Hospital App";
        String body = "You have a new notification";

        if (remoteMessage.getNotification() != null) {

            if (remoteMessage.getNotification().getTitle() != null)
                title = remoteMessage.getNotification().getTitle();

            if (remoteMessage.getNotification().getBody() != null)
                body = remoteMessage.getNotification().getBody();
        }

        showNotification(title, body);
    }

    private void showNotification(String title, String message) {

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Hospital Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}