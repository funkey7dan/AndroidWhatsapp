package com.example.myapplication.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.API.UnsafeOkHttpClient;
import com.example.myapplication.API.WebServiceAPI;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.addContactRequest;
import com.example.myapplication.entities.inviteContact;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.utils.RetrofitSingleton;

import java.net.URL;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsDialog extends AppCompatDialogFragment {

    private EditText etUsername;
    private EditText etNickname;
    private EditText etServer;
    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_contact, null);
        etUsername = view.findViewById(R.id.edit_contact_name);
        etNickname = view.findViewById(R.id.edit_contact_nickname);
        etServer = view.findViewById(R.id.edit_contact_server);
        builder.setView(view)
                .setTitle("Add Contact")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String server = etServer.getText().toString();
                        String uname = etUsername.getText().toString();
                        String nickname = etNickname.getText().toString();
                        addContact(uname, server, nickname);
                        inviteContact(uname, server, nickname);
                    }
                });
//        etUsername = view.findViewById(R.id.edit_contact_name);
//        etNickname = view.findViewById(R.id.edit_contact_nickname);
//        etServer = view.findViewById(R.id.edit_contact_server);
        return builder.create();
    }

    public void addContact(String uname, String server, String nickname) {
        Retrofit retrofit = RetrofitSingleton.getInstance();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<Void> call = webServiceAPI.addContact(new addContactRequest(uname, nickname, server));
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

    public void inviteContact(String uname, String server, String nickname) {
        DataSingleton data = DataSingleton.getInstance();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        String host = server.split(":")[0];
        if (Objects.equals(host, "localhost")) {
            host = "10.0.2.2";
        }
        String port = server.split(":")[1];
        URL url = new HttpUrl.Builder().scheme("https").host(host).port(Integer.parseInt(port)).addPathSegments("api/").build().url();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<Void> call = webServiceAPI.inviteContact(new inviteContact(data.getUser(), uname, url.toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("invite1", response.toString());
                Log.d("invite2", response.headers().toString());
                if (response.code() == 201 || response.code() == 200) {
                    Toast.makeText(MyApplication.getContext(), "Success!", Toast.LENGTH_LONG).show();
                    listener.apply(uname, nickname, server);
                    //listener.apply(uname, nickname, server);
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


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement");
        }
    }

    public interface DialogListener {
        void apply(String uname, String nickname, String server);
    }



}
