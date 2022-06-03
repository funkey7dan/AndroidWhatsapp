package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    // buttons
    Button bRegister;

    // text fields
    EditText etUsername, etPassword;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // need registration?
        TextView register = findViewById(R.id.tvLogin);
        register.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,
                RegisterActivity.class)));

        // validations
        // register buttons with their proper IDs.
        bRegister = findViewById(R.id.sendButton);

        // register all the EditText fields with their IDs.
        etUsername = findViewById(R.id.inputUsername);
        etPassword = findViewById(R.id.inputPassword);

        // handle the PROCEED button
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(LoginActivity.this, ContactsListActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (etUsername.length() == 0) {
            etUsername.setError("This field is required");
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        }

        // TODO: real validation against the server

        // after all validation return true.
        return true;
    }
}