package com.example.myapplication.API;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.addContactRequest;
import com.example.myapplication.entities.inviteContact;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.utils.MyApplication;
import com.example.myapplication.utils.RetrofitSingleton;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    private MutableLiveData<List<Contact>> contactsListData;
    private ContactDao contactDao;
    Retrofit retrofit = RetrofitSingleton.getInstance();
    WebServiceAPI webServiceAPI;


    public ContactsAPI(ContactDao contactDao) {
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
//                    contactDao.clearContacts();
                    try {
                        contactDao.insertContactsList(response.body());
                    } catch (NullPointerException e) {
                        Log.d("onResponese GEt", "onResponse: ");
                        e.printStackTrace();
                    }
//                    contactsListData.postValue(response.body());
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

    public void addContact(Contact contact) {
        Retrofit retrofit = RetrofitSingleton.getInstance();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<Void> call = webServiceAPI.addContact(new addContactRequest
                (contact.getId(), contact.getName(), contact.getServer()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("invite1", response.toString());
                Log.d("invite2", response.headers().toString());
                if (response.code() == 201 || response.code() == 200) {
                    Toast.makeText(MyApplication.getContext(), "Success!", Toast.LENGTH_LONG).show();
//                    contactDao.insertSingleContact(contact);
                } else if (response.code() == 404) {
                    Toast.makeText(MyApplication.getContext(), "Contact doesn't exist!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "Response code isn't 200", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                Toast.makeText(MyApplication.getContext(), "Request failed!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void inviteContact(Contact contact) {
        DataSingleton data = DataSingleton.getInstance();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        String host = contact.getServer().split(":")[0];
        if (Objects.equals(host, "localhost")) {
            host = "10.0.2.2";
        }
        String port = contact.getServer().split(":")[1];
        URL url = new HttpUrl.Builder().scheme("https").host(host).port
                (Integer.parseInt(port)).addPathSegments("api/").build().url();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<Void> call = webServiceAPI.inviteContact(new inviteContact
                (data.getUser(), contact.getId(), url.toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("invite1", response.toString());
                Log.d("invite2", response.headers().toString());
                if (response.code() == 201 || response.code() == 200) {
                    Toast.makeText(MyApplication.getContext(), "Success!", Toast.LENGTH_LONG).show();
                } else if (response.code() == 404) {
                    Toast.makeText(MyApplication.getContext(), "Contact doesn't exist!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "Response code isn't 200", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                Toast.makeText(MyApplication.getContext(), "Request failed!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void sendToken(String token) {
        Retrofit retrofit = RetrofitSingleton.getInstance();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<Void> call = webServiceAPI.sendToken(new MessageRequest(token));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("token send1", response.toString());
                Log.d("token send2", response.headers().toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
