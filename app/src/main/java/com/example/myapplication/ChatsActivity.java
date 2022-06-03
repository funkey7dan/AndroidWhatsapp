package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        // Populate dummy messages in List, you can implement your code here
        ArrayList<MessageModel> messagesList = new ArrayList<>();
        for (int i=0;i<10;i++) {
            messagesList.add(new MessageModel("Hi", i % 2 == 0 ? MessagesAdapter.MESSAGE_TYPE_IN : MessagesAdapter.MESSAGE_TYPE_OUT));
        }

        MessagesAdapter adapter = new MessagesAdapter(this, messagesList);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}