package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ContactsListActivity extends AppCompatActivity implements ContactsDialog.DialogListener {
    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    ImageButton logout,settings;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;

    public static class ContactsViewModel extends ViewModel {
        private MutableLiveData<List<Contact>> contacts;

        public MutableLiveData<List<Contact>> getContacts(){
            if (contacts == null){
                contacts = new MutableLiveData<>();
//                contacts.setValue(contactDao.getContacts());
            }
            return contacts;
        }
    }
//    public MutableLiveData<List<Contact>> contacts;
    public ContactsViewModel contactsViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ((TextView)findViewById(R.id.contactsName)).setText(data.getUser());
        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        logout = findViewById(R.id.logoutButton);
        // Logout function
        logout.setOnClickListener(v -> {
            data.setUser(null);
            Intent i = new Intent(ContactsListActivity.this, LoginActivity.class);
            startActivity(i);
        });
        settings = findViewById(R.id.settingsButton);
        recyclerView = findViewById(R.id.contactsList);
        ProgressBar loadSpinner = findViewById(R.id.progressBar);
        contactsViewModel.getContacts().setValue(contactDao.getContacts());
        MyAdapter myAdapter = new MyAdapter(this, contactDao.getContacts());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsViewModel.getContacts().observe(this, contacts -> {
            myAdapter.setData(contactsViewModel.getContacts().getValue());
        });
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
     *  load contacts to local data base form server and refresh view
     */
    public void loadContacts(){
        isLoading.setValue(true);
        // TODO: add contact fetching via api
    }

    @Override
    public void apply(String uname, String nickname, String server) {
        contactDao.insertSingle(new Contact(uname,nickname,server));
        //recreate();
        contactsViewModel.getContacts().setValue(contactDao.getContacts());
    }

    public void openDialog() {
        ContactsDialog contactsDialog = new ContactsDialog();
        contactsDialog.show(getSupportFragmentManager(), "add contact dialog");
    }
}