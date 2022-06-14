package com.example.myapplication.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.AppDB;
import com.example.myapplication.API.ContactDao;
import com.example.myapplication.API.ContactsViewModel;
import com.example.myapplication.R;
import com.example.myapplication.adapters.MessagesAdapter;
import com.example.myapplication.entities.Message;
import com.example.myapplication.utils.DataSingleton;

public class MessagesActivity extends AppCompatActivity {

    DataSingleton data = DataSingleton.getInstance();
    RecyclerView recyclerView;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private AppDB db;
    private ContactDao contactDao;
    public ContactsViewModel contactsViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(Math.max(adapter.getItemCount() - 1, 0));
                        }
                    },50);
                }
            }
        });

        TextView contactsName = findViewById(R.id.contactsName);
        contactsName.setText(getIntent().getExtras().getString("nickname"));


//        setContentView(R.layout.activity_chats);
        ProgressBar loadSpinner = findViewById(R.id.progressBar);
        ImageView imageView = findViewById(R.id.chatContactImage);
        imageView.setClipToOutline(true);

        EditText messageInput = findViewById(R.id.messageInput);
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            String messageText = messageInput.getText().toString();
            if (messageText.isEmpty()) return;
            contactsViewModel.addMessage(new Message(messageText, data.getActiveContact(),
                    data.getUser(), data.getActiveContact()));
            messageInput.setText("");
        });

        contactsViewModel.getMessages().observe(this, messages -> {
            if (messages != null) {
                adapter.setData(messages.messages);
            }
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });

        // observe the loading state and hide the loading spinner if loading is complete
        isLoading.observe(this, state -> {
            if (state) {
                loadSpinner.setVisibility(View.VISIBLE);
            } else {
                loadSpinner.setVisibility(View.INVISIBLE);
            }
        });
    }
}