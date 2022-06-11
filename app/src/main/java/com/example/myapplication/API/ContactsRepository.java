package com.example.myapplication.API;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;

import java.util.List;
import java.util.Objects;

public class ContactsRepository {
    DataSingleton data = DataSingleton.getInstance();
    private ContactDao contactDao;
    private AppDB db;
    private MutableLiveData<List<Contact>> contacts;
    private MutableLiveData<ContactWIthMessages> messages;


    public ContactsRepository(Context applicationContext) {
        db = Room.databaseBuilder(applicationContext, AppDB.class, data.getUser() + "_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        this.contacts = null;
        //contactListData = new ContactListData();
    }


    public MutableLiveData<List<Contact>> getAllContacts() {
        if (this.contacts == null) {
            this.contacts = new MutableLiveData<>(contactDao.getContacts().getValue());
        }
        return contacts;
    }

    public MutableLiveData<ContactWIthMessages> getAllMessages() {
        if (this.messages == null) {
            this.messages = new MutableLiveData<>(contactDao.getChatWith(data.getActiveContact()).getValue());
            if (messages.getValue() == null) {
                messages.setValue(new ContactWIthMessages());
            }
        }
        return messages;
    }

    public void addContact(Contact contact) {
        Objects.requireNonNull(this.contacts.getValue()).add(contact);
        contactDao.insertSingle(contact);
    }

    public void addMessage(Message message) {
        Objects.requireNonNull(this.messages.getValue()).messages.add(message);
        contactDao.insertMessage(message);
    }
}
