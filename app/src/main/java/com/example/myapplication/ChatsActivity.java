package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.API.ContactsViewModel;
import com.example.myapplication.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;
    public ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        setContentView(R.layout.activity_chats);

//        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
//        contactDao = db.contactDao();

        recyclerView = findViewById(R.id.recycler_view);
        MessagesAdapter adapter = new MessagesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        TextView contactsName = findViewById(R.id.contactsName);
        contactsName.setText(getIntent().getExtras().getString("nickname"));


//        setContentView(R.layout.activity_chats);
        ProgressBar loadSpinner = findViewById(R.id.progressBar);
        ImageView imageView = findViewById(R.id.chatContactImage);
        imageView.setClipToOutline(true);

        EditText messageInput = findViewById(R.id.messageInput);
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInput.getText().toString();
                if (messageText.isEmpty()) return;
                contactsViewModel.addMessage(new Message(messageText, data.getActiveContact()));
            }
        });

        contactsViewModel.getMessages().observe(this, messages -> {
            adapter.setData(messages.messages);
        });

        // Populate dummy messages in List, you can implement your code here
        //ArrayList<Message> messagesList = new ArrayList<>();
//        List<Message> messagesList = contactDao.getChatWith(data.getActiveContact()).messages;

        // observe the loading state and hide the loading spinner if loading is complete
        isLoading.observe(this, state -> {
            if (state){
                loadSpinner.setVisibility(View.VISIBLE);
            }
            else{
                loadSpinner.setVisibility(View.INVISIBLE);
            }
        });


    }

    /**
     *  load contacts to local data base form server and refresh view
     */
    public void loadMessages(){
        isLoading.setValue(true);
        // TODO: add message fetching via api

    }
}