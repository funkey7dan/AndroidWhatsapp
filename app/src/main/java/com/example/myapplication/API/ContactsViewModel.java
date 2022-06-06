package com.example.myapplication.API;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {
    private ContactsRepository repository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel (Application application) {
        super(application);
        repository = new ContactsRepository(application.getApplicationContext());
        contacts = repository.getAll();
    }

    public LiveData<List<Contact>> get() { return contacts; }

    public void add(Contact contact) { repository.add(contact); }
}