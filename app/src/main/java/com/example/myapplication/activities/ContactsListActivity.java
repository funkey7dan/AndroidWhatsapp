package com.example.myapplication.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ContactsViewModel;
import com.example.myapplication.API.UnsafeOkHttpClient;
import com.example.myapplication.API.WebServiceAPI;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ContactsAdapter;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.utils.MyApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContactsListActivity extends AppCompatActivity implements ContactsDialog.DialogListener {
    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    ImageButton logout, settings;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private ContactsAdapter contactsAdapter;
    public ContactsViewModel contactsViewModel;
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            contactsViewModel.updateContacts();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter.create("Message", "application/MyType"));
        ((TextView) findViewById(R.id.contactsName)).setText(data.getUser());
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        logout = findViewById(R.id.logoutButton);
        // Logout function TODO: need to add logout API, with removal of FB token
        logout.setOnClickListener(v -> {
            logout();
            Intent i = new Intent(ContactsListActivity.this, LoginActivity.class);
            startActivity(i);
        });
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("token failed", "Fetching FCM registration token failed", task.getException());
                return;
            }

            // Get new FCM registration token
            String token = task.getResult();

            // Log and toast
            String msg = token;
            Log.d("token created", msg);
            contactsViewModel.sendToken();
        });
        settings = findViewById(R.id.settingsButton);
        recyclerView = findViewById(R.id.contactsList);
        contactsAdapter = new ContactsAdapter(this);
        recyclerView.setAdapter(contactsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView usersName = findViewById(R.id.contactsName);
        usersName.setText(data.getUser());

        contactsViewModel.get().observe(this, contactsAdapter::setData);


        ProgressBar loadSpinner = findViewById(R.id.progressBar);

        ImageView myImage = findViewById(R.id.myImage);
        myImage.setClipToOutline(true);

        // observe the loading state and hide the loading spinner if loading is complete
        isLoading.observe(this, state -> {
            if (state){
                loadSpinner.setVisibility(View.VISIBLE);
            }
            else{
                loadSpinner.setVisibility(View.INVISIBLE);
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingAddContact);
        fab.setOnClickListener(v -> openDialog());
    }


    @Override
    public void apply(String uname, String nickname, String server) {
        contactsViewModel.add(new Contact(uname,nickname,server));
    }

    public void openDialog() {
        ContactsDialog contactsDialog = new ContactsDialog();
        contactsDialog.show(getSupportFragmentManager(), "add contact dialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //contactsViewModel.get().observe(this, myAdapter::setData);
        contactsViewModel.updateContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        logout();
    }


    public void logout() {
        data.setUser(null);
        data.setToken(null);
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.getContext().getString(R.string.BaseUrlNoAPI))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<Void> call = webServiceAPI.logout();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("logout get", response.toString());
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

