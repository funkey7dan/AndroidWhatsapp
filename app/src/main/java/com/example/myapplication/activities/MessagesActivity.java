package com.example.myapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.API.ContactsAPI;
import com.example.myapplication.API.ContactsViewModel;
import com.example.myapplication.API.MessagesAPI;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.adapters.MessagesAdapter;
import com.example.myapplication.R;
import com.example.myapplication.entities.Message;

import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {

    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;
    public ContactsViewModel contactsViewModel;
    public MessagesAPI api;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        setContentView(R.layout.activity_chats);
        api = new MessagesAPI(contactsViewModel.getMessagesWith(data.getActiveContact()), contactDao);
        loadMessages();
        recyclerView = findViewById(R.id.recycler_view);
        MessagesAdapter adapter = new MessagesAdapter(this);
        try {
            adapter.setData(contactsViewModel.getMessagesWith(data.getActiveContact()).getValue());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);

        TextView contactsName = findViewById(R.id.contactsName);
        contactsName.setText(getIntent().getExtras().getString("nickname"));

        ProgressBar loadSpinner = findViewById(R.id.progressBar);
        ImageView imageView = findViewById(R.id.chatContactImage);
        imageView.setClipToOutline(true);

        EditText messageInput = findViewById(R.id.messageInput);
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            String messageText = messageInput.getText().toString();
            if (messageText.isEmpty()) return;
            //contactsViewModel.addMessage(new Message(messageText, data.getActiveContact()));
            api.post(data.getActiveContact(), messageText);
            messageInput.setText("");
        });

        // if the messages in the contact view changed
        contactsViewModel.getMessagesWith(data.getActiveContact()).observe(this, messages -> {
            if (messages != null) {
                adapter.setData(messages);
            }
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });

        // observe the loading state and hide the loading spinner if loading is complete
        isLoading.observe(this, state -> {
            if (state) {
                loadSpinner.setVisibility(View.VISIBLE);
            } else{
                loadSpinner.setVisibility(View.INVISIBLE);
            }
        });


    }

    /**
     *  load contacts to local data base form server and refresh view
     */
    public void loadMessages() {
        isLoading.setValue(true);
        api.get(data.getActiveContact());
        isLoading.setValue(false);
    }
}