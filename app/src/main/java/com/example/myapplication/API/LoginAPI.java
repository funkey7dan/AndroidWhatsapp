package com.example.myapplication.API;

import android.text.Editable;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public Boolean result;

    public LoginAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public Boolean post(String username, String password) {
        Call<Void> call = webServiceAPI.login(new LoginRequest(username, password));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                result = true;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result = false;
            }
        });
        return result;
    }
}
