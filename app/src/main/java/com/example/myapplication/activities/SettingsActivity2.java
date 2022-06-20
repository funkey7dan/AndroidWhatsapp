package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utils.RetrofitSingleton;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;

public class SettingsActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        Context context = getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        EditText server = findViewById(R.id.inputServer);
        SharedPreferences.Editor editor = preferences.edit();
        String host = "foo";
        Button save = findViewById(R.id.sendButton2);
        save.setOnClickListener(v -> {
            String newServer = server.getText().toString();
            String newHost = newServer.split(":")[0];
            String newPort = newServer.split(":")[1];
            URL url = new HttpUrl.Builder().scheme("https").host(newHost).port
                    (Integer.parseInt(newPort)).addPathSegments("api/").build().url();
            editor.putString("server", url.toString());
            editor.apply();
            RetrofitSingleton.setBaseUrl(url.toString());
            Intent i = new Intent(SettingsActivity2.this, ContactsListActivity.class);
            startActivity(i);
        });
        try {
            URL url1 = new URL(context.getString(R.string.BaseUrlNoAPI));
            host = url1.getHost() + ":" + url1.getPort();
            URL url = new URL(preferences.getString("server", host));
            host = url.getHost() + ":" + url.getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        server.setHint(host);
    }
}