package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();

        setContentView(R.layout.activity_chats);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        ImageView imageView = findViewById(R.id.chatContactImage);
        imageView.setClipToOutline(true);

        // Populate dummy messages in List, you can implement your code here
        //ArrayList<Message> messagesList = new ArrayList<>();
        List<Message> messagesList = contactDao.getChatWith(data.getActiveContact()).messages;
//        for (int i=0;i<10;i++) {
//            messagesList.add(new Message("Hi", i % 2 == 0 ? MessagesAdapter.MESSAGE_TYPE_IN : MessagesAdapter.MESSAGE_TYPE_OUT));
//        }
        // observe the loading state and hide the loading spinner if loading is complete
        isLoading.observe(this, state -> {
            if (state){
                progressBar.setVisibility(View.VISIBLE);
            }
            else{
                progressBar.setVisibility(View.INVISIBLE);
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