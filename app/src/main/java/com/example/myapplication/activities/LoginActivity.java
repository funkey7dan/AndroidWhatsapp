package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.API.LoginAPI;
import com.example.myapplication.API.WebServiceAPI;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.LoginRequest;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.utils.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



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
                CheckAllFields();
            }
        });
    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private void CheckAllFields() {
        if (etUsername.length() == 0) {
            etUsername.setError("This field is required");
            return;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return;
        }
        WebServiceAPI webServiceAPI;
        Retrofit retrofit = RetrofitSingleton.getInstance();
        webServiceAPI = retrofit.create(WebServiceAPI.class);

        Call<Void> call = webServiceAPI.login(new LoginRequest(etUsername.getText().toString()
                ,etPassword.getText().toString()));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("tag1",response.toString());
                Log.d("tag2",response.headers().toString());
                if (response.code()==200){
                    data.setUser(etUsername.getText().toString());
                    Intent i = new Intent(LoginActivity.this, ContactsListActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MyApplication.getContext(), "Username/Password is incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                Toast.makeText(MyApplication.getContext(),"Request failed!",Toast.LENGTH_LONG).show();
            }
        });

    }

}