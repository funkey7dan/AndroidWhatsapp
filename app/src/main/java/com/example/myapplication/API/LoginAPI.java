package com.example.myapplication.API;

import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.LoginRequest;
import com.example.myapplication.utils.RetrofitSingleton;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit = RetrofitSingleton.getInstance();
    WebServiceAPI webServiceAPI;
    public Boolean result;

    public LoginAPI() {
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    public Boolean post(String username, String password) {
        Call<Void> call = webServiceAPI.login(new LoginRequest(username, password));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("tag1",response.toString());
                Log.d("tag2",response.headers().toString());
                if (response.code()==200){
                    Toast.makeText(MyApplication.getContext(), "Successful login", Toast.LENGTH_LONG).show();
                    result = true;
                }
                else{
                    Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_LONG).show();
                    result = false;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result = false;
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                Toast.makeText(MyApplication.getContext(),"Request failed",Toast.LENGTH_LONG).show();
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
