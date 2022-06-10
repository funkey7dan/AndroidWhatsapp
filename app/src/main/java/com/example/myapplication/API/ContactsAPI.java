package com.example.myapplication.API;

import android.provider.ContactsContract;

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
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;


    public ContactsAPI(MutableLiveData<List<Contact>> contactsListData, ContactDao contactDao) {
        this.contactsListData = contactsListData;
        this.contactDao = contactDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public ContactsAPI(ContactDao contactDao) {
        this.contactDao = contactDao;
        retrofit = RetrofitSingleton.getInstance();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        String auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbGljZSIsImp0aSI6ImYzNzBhZGY3LWY4ODQtNDYyMC05MmUxLTZhMjVkZDE4OWE2ZCIsImlhdCI6IjE2NTQ3NjU1MzIiLCJVc2VySWQiOiJhbGljZSIsImV4cCI6MTY1NDc2OTEzMiwiaXNzIjoiRm9vIiwiYXVkIjoiQmFyIn0.A-ySQHcxdwv8Gpoucp90JhhbEsIgkji7OnFAZ-V5feU";
        Call<List<Contact>> call = webServiceAPI.getContacts(auth);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                new Thread(() -> {
                    //contactDao.clear(); TODO implement ?
                    Log.d("Contacts response",response.toString());
                    Log.d("Contacts headers",response.headers().toString());
                    contactDao.insertList(response.body());
                    contactsListData.postValue(contactDao.getContacts().getValue());
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
            }
        });

    }

}
