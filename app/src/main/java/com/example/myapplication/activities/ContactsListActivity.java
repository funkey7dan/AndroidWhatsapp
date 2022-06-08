package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.API.ContactsViewModel;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.adapters.MyAdapter;
import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactsListActivity extends AppCompatActivity implements ContactsDialog.DialogListener {
    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;
//    public MutableLiveData<List<Contact>> contacts;
    // TODO: check if it can be private
    public ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
//        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
//        contactDao = db.contactDao();
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        recyclerView = findViewById(R.id.contactsList);
        final MyAdapter myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView usersName = findViewById(R.id.contactsName);
        usersName.setText(data.getUser());

        contactsViewModel.get().observe(this, contacts -> {
            // TODO: check whether we need to fill it
            myAdapter.setData(contacts);
        });


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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                //isLoading.setValue(Boolean.FALSE.equals(isLoading.getValue())); //tester to toggle loading state
            }
        });
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
        contactsViewModel.add(new Contact(uname,nickname,server));
    }

    public void openDialog() {
        ContactsDialog contactsDialog = new ContactsDialog();
        contactsDialog.show(getSupportFragmentManager(), "add contact dialog");
    }
}