package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
            Context context = MyApplication.getContext();
            okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            retrofit = new Retrofit.Builder()
                    .baseUrl(preferences.getString("server", context.getString(R.string.BaseUrl)))
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static void setBaseUrl(String newUrl) {
        try {
            Context context = MyApplication.getContext();
            okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            retrofit = new Retrofit.Builder()
                    .baseUrl(newUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
