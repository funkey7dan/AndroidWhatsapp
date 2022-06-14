package com.example.myapplication.services;


import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.utils.DataSingleton;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {
    DataSingleton data = DataSingleton.getInstance();
    LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

    public FirebaseService() {
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

        Log.d("message received", String.valueOf(message));
        if (message.getNotification() != null) {
            message.getNotification().getTitle().equals("New Message");
            broadcastManager.sendBroadcast(new Intent("Message")
                    .putExtra("sentFrom", message.getData().get("sentFrom"))
                    .putExtra("content", message.getData().get("content"))
                    .setType("application/MyType"));
        }
    }

}