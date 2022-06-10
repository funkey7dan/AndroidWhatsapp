package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.WebServiceAPI;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.LoginRequest;
import com.example.myapplication.utils.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegisterActivity extends AppCompatActivity {

    // buttons
    Button bRegister;

    // text fields
    EditText etUsername, etPassword, etConfirmPassword;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // already registered?
        TextView login = findViewById(R.id.tvLogin);
        login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this,
                LoginActivity.class)));

        // validations
        // register buttons with their proper IDs.
        bRegister = findViewById(R.id.sendButton);

        // register all the EditText fields with their IDs.
        etUsername = findViewById(R.id.inputUsername);
        etPassword = findViewById(R.id.inputPassword);
        etConfirmPassword = findViewById(R.id.inputConfirmPassword);

        // handle the PROCEED button
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                CheckAllFields();

                /*// the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "User registered successfully",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(i);
                }*/
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
        } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Passwords are not matching");
            return false;
        }
        // after all validation return true.
        WebServiceAPI webServiceAPI;
        Retrofit retrofit = RetrofitSingleton.getInstance();
        webServiceAPI = retrofit.create(WebServiceAPI.class);

        Call<Void> call = webServiceAPI.register(new LoginRequest(etUsername.getText().toString()
                ,etPassword.getText().toString()));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("tag1",response.toString());
                Log.d("tag2",response.headers().toString());
                if (response.code()==200){
                    Toast.makeText(MyApplication.getContext(), "Success", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MyApplication.getContext(), "User already exists!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                Toast.makeText(MyApplication.getContext(),"Request failed",Toast.LENGTH_LONG).show();
            }
        });




        return true;
    }
}