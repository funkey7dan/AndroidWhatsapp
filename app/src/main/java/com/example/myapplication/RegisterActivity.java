package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView login = findViewById(R.id.tvLogin);
        login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this,
                LoginActivity.class)));
    }
}