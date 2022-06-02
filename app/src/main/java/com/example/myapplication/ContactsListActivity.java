package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;

public class ContactsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[] = {"Alice", "Bob", "Charlie", "Dan", "Eve", "Freddie", "Giovanni", "Herbert"};
    String s2[] = {"I'm alice", "I'm Bob", "I'm Charlie", "I'm Dan", "I'm Eve", "I'm Freddie", "I'm Giovanni", "I'm Herbert"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        recyclerView = findViewById(R.id.contactsList);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView myImage = findViewById(R.id.myImage);
        myImage.setClipToOutline(true);

    }
}