package com.example.myapplication.API;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.TransferRequest;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.utils.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MessagesAPI {
    public DataSingleton data = DataSingleton.getInstance();
    private final ContactDao contactDao;
    Retrofit retrofit = RetrofitSingleton.getInstance();
    WebServiceAPI webServiceAPI;

    public MessagesAPI(ContactDao contactDao) {
        this.contactDao = contactDao;
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(String id) {
        Call<List<Message>> call = webServiceAPI.getMessagesWith(id);
        call.enqueue(new Callback<List<Message>>() {

            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                new Thread(() -> {
                    Log.d("Message response", response.toString());
                    Log.d("Message headers", response.headers().toString());
                    if (response.code() == 200) {
                        List<Message> list = response.body();
                        assert list != null;
                        for (Message m: list) {
                            m.setContactId(id);
                        }
                        contactDao.insertMessagesList(list);
                    }

//                    messagesListData.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void post(String id, String message) {
        Call<Void> call = webServiceAPI.sendMessage(id, new MessageRequest(message));
        call.enqueue(new Callback<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Thread(() -> {
                    Log.d("Message response", response.toString());
                    Log.d("Message headers", response.headers().toString());
//                    messagesListData.getValue().add(new Message(message, id));
                }).start();
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

    public void transfer(String id,String sender,String message){
        Call<Void> call = webServiceAPI.transferMessage(
                new TransferRequest(sender,id,message));
        call.enqueue(new Callback<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Thread(() -> {
                    Log.d("Message response", response.toString());
                    Log.d("Message headers", response.headers().toString());
//                    messagesListData.getValue().add(new Message(message, id));
                }).start();
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
