package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        ProgressBar loadSpinner = findViewById(R.id.progressBar);
        ImageView imageView = findViewById(R.id.chatContactImage);
        imageView.setClipToOutline(true);

        // Populate dummy messages in List, you can implement your code here
        ArrayList<MessageModel> messagesList = new ArrayList<>();
        for (int i=0;i<10;i++) {
            messagesList.add(new MessageModel("Hi", i % 2 == 0 ? MessagesAdapter.MESSAGE_TYPE_IN : MessagesAdapter.MESSAGE_TYPE_OUT));
        }
        // observe the loading state and hide the loading spinner if loading is complete
        isLoading.observe(this, state -> {
            if (state){
                loadSpinner.setVisibility(View.VISIBLE);
            }
            else{
                loadSpinner.setVisibility(View.INVISIBLE);
            }
        });
        MessagesAdapter adapter = new MessagesAdapter(this, messagesList);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    /**
     *  load contacts to local data base form server and refresh view
     */
    public void loadMessages(){
        isLoading.setValue(true);
        // TODO: add message fetching via api

    }
}