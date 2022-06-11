package com.example.myapplication.API;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {
    private final ContactsRepository repository;
    private final MutableLiveData<List<Contact>> contacts;
    private final MutableLiveData<ContactWIthMessages> messages;

    public ContactsViewModel(Application application) {
        super(application);
        repository = new ContactsRepository(application.getApplicationContext());
        contacts = repository.getAllContacts();
        messages = repository.getAllMessages();
    }

    public MutableLiveData<List<Contact>> get() {
        return contacts;
    }

    public MutableLiveData<ContactWIthMessages> getMessages() {
        return messages;
    }

    public void add(Contact contact) {
        repository.addContact(contact);
    }

    public void addMessage(Message message) {
        repository.addMessage(message);
    }

}