package com.example.myapplication.API;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.utils.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    private MutableLiveData<List<Contact>> contactsListData;
    private final ContactDao contactDao;
    Retrofit retrofit = RetrofitSingleton.getInstance();
    WebServiceAPI webServiceAPI;


    public ContactsAPI(MutableLiveData<List<Contact>> contactsListData, ContactDao contactDao) {
        this.contactsListData = contactsListData;
        this.contactDao = contactDao;
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<Contact>> call = webServiceAPI.getContacts();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                new Thread(() -> {
                    Log.d("Contacts response", response.toString());
                    Log.d("Contacts headers", response.headers().toString());
                    //contactDao.insertList(response.body());
                    contactsListData.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
