package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.API.ContactsAPI;
import com.example.myapplication.API.ContactsViewModel;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.adapters.MyAdapter;
import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactsListActivity extends AppCompatActivity implements ContactsDialog.DialogListener {
    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    ImageButton logout,settings;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;
//    public MutableLiveData<List<Contact>> contacts;
// TODO: check if it can be private
public ContactsViewModel contactsViewModel;
    public ContactsAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, data.getUser() + "_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        ((TextView) findViewById(R.id.contactsName)).setText(data.getUser());
        ContactsAPI contactsAPI = new ContactsAPI(contactsViewModel.get(), contactDao);
        api = contactsAPI;
        loadContacts(contactsAPI);
        logout = findViewById(R.id.logoutButton);
        // Logout function
        logout.setOnClickListener(v -> {
            data.setUser(null);
            Intent i = new Intent(ContactsListActivity.this, LoginActivity.class);
            startActivity(i);
        });
        settings = findViewById(R.id.settingsButton);
        recyclerView = findViewById(R.id.contactsList);
        final MyAdapter myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView usersName = findViewById(R.id.contactsName);
        usersName.setText(data.getUser());

        contactsViewModel.get().observe(this, myAdapter::setData);


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

    /**
     * load contacts to local data base form server and refresh view
     */
    public void loadContacts(ContactsAPI api) {
        isLoading.setValue(true);
        api.get();
        // TODO: add contact fetching via api
        isLoading.setValue(false);
    }

    @Override
    public void apply(String uname, String nickname, String server) {
        api.get();
        contactsViewModel.get();
        contactsViewModel.add(new Contact(uname, nickname, server));
    }

    public void openDialog() {
        ContactsDialog contactsDialog = new ContactsDialog();
        contactsDialog.show(getSupportFragmentManager(), "add contact dialog");
    }
}