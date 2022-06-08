package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.API.LoginAPI;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.R;


public class LoginActivity extends AppCompatActivity {

    DataSingleton data = DataSingleton.getInstance();
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
        LoginAPI api = new LoginAPI();
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
                isAllFieldsChecked = CheckAllFields(api);
                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    data.setUser(etUsername.getText().toString());
                    Intent i = new Intent(LoginActivity.this, ContactsListActivity.class);
                    startActivity(i);

                }
            }
        });
    }
    // TODO: implement async task for login :
    // https://developer.android.com/reference/android/os/AsyncTask
    class LoginTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {

            return result;
        }

        protected void onPostExecute(Long result) {
        }


    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private Boolean CheckAllFields(LoginAPI api) {
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
        boolean result = api.post(etUsername.getText().toString(), etPassword.getText().toString());
        return result;
    }

}