package com.example.myapplication.services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.R;
import com.example.myapplication.utils.DataSingleton;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FirebaseService extends FirebaseMessagingService {
    DataSingleton data;
    LocalBroadcastManager broadcastManager;
    final String CHANNEL_ID = "MESSAGE_NOTIFICATION";


    public FirebaseService() {
        data = DataSingleton.getInstance();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("Token refresh", "Refreshed token: " + token);
        super.onNewToken(token);
        data.setToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        createNotificationChannel();

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Message Notification",
                NotificationManager.IMPORTANCE_HIGH
        );
        notificationManager.createNotificationChannel(channel);


        Log.d("message received", String.valueOf(message));
        if (message.getNotification() != null) {
            if (message.getNotification().getTitle().equals("New Message")) {
                broadcastManager.sendBroadcast(new Intent("Message")
                        .putExtra("sentFrom", message.getData().get("sentFrom"))
                        .putExtra("content", message.getData().get("content"))
                        .setType("application/MyType"));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification_plane)
                        .setContentTitle(message.getData().get("sentFrom"))
                        .setContentText(message.getData().get("content"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE);
                int oneTimeID = (int) SystemClock.uptimeMillis();
                NotificationManagerCompat.from(this).notify(oneTimeID, builder.build());
            } else {
                broadcastManager.sendBroadcast(new Intent("Message")
                        .setType("application/MyType"));
            }

        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}