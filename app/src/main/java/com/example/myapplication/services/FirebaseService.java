package com.example.myapplication.services;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.utils.DataSingleton;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {
    DataSingleton data = DataSingleton.getInstance();

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
    }
}