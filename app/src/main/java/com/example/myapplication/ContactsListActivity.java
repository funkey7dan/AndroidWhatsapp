package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    String s1[] = {"Alice", "Bob", "Charlie", "Dan", "Eve", "Freddie", "Giovanni", "Herbert"};
    String s2[] = {"I'm alice", "I'm Bob", "I'm Charlie", "I'm Dan", "I'm Eve", "I'm Freddie", "I'm Giovanni", "I'm Herbert"};
    String s3[] = {"12:44", "12:44", "12:44", "12:44", "12:44", "12:44", "12:44", "12:44"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        recyclerView = findViewById(R.id.contactsList);
        ProgressBar loadSpinner = findViewById(R.id.progressBar);
        MyAdapter myAdapter = new MyAdapter(this, s1, s2, s3);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                //openDialog(); // TODO return button functionality
                isLoading.setValue(Boolean.FALSE.equals(isLoading.getValue())); //tester to toggle loading state

//                Toast toast = Toast.makeText(getApplicationContext(),
//                        "Add Contact",
//                        Toast.LENGTH_SHORT);
//
//                toast.show();
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

    public void openDialog() {
        ContactsDialog contactsDialog = new ContactsDialog();
        contactsDialog.show(getSupportFragmentManager(), "add contact dialog");
    }
}