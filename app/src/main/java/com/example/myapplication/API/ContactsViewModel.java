package com.example.myapplication.API;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {
    private ContactsRepository repository;
    private LiveData<List<Contact>> contacts;
    private LiveData<ContactWIthMessages> messages;

    public ContactsViewModel (Application application) {
        super(application);
        repository = new ContactsRepository(application.getApplicationContext());
        contacts = repository.getAllContact();
        messages = repository.getAllMessages();
    }

    public LiveData<List<Contact>> get() { return contacts; }

    public void updateContacts() { repository.getAllContact(); }

    public void updateMessages() { repository.getAllMessages(); }

    public LiveData<ContactWIthMessages> getMessages() { return messages; }

    public void add(Contact contact) { repository.addContact(contact); }
    public void addMessage(Message message) { repository.addMessage(message); }

}