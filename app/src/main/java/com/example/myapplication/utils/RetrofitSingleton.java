package com.example.myapplication.utils;

import com.example.myapplication.API.UnsafeOkHttpClient;
import com.example.myapplication.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;


    public static Retrofit getInstance() {
        if (retrofit == null)
        {
            okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(MyApplication.getContext().getString(R.string.BaseUrl))
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
