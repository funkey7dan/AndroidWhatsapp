package com.example.myapplication.API;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContactsRepository {
    DataSingleton data = DataSingleton.getInstance();
    private ContactDao contactDao;
    private AppDB db;
    private MutableLiveData<List<Contact>> contacts;
    private final Map<String, ContactWIthMessages> contactWIthMessagesMap;


    public ContactsRepository(Context applicationContext) {
        db = Room.databaseBuilder(applicationContext, AppDB.class, data.getUser() + "_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        this.contactWIthMessagesMap = new HashMap<>();
        this.contacts = null;
        //contactListData = new ContactListData();
    }


    public MutableLiveData<List<Contact>> getAllContacts() {
        if (this.contacts == null) {
            this.contacts = new MutableLiveData<>(contactDao.getContacts().getValue());
            if (this.contacts.getValue() != null) {
                for (Contact c : Objects.requireNonNull(this.contacts.getValue())
                ) {
                    if (!contactWIthMessagesMap.containsKey(c.getId())) {
                        contactWIthMessagesMap.put(c.getId(), new ContactWIthMessages(c));
                    }
                }
            }
        }
        // generate a list of messages for all contacts
        return contacts;
    }

    public Map<String, ContactWIthMessages> getAllMessages() {
        return contactWIthMessagesMap;
    }

    public void addContact(Contact contact) {
        Objects.requireNonNull(this.contacts.getValue()).add(contact);
        contactDao.insertSingle(contact);
        contacts.getValue().add(contact);
        contactWIthMessagesMap.put(contact.getId(), new ContactWIthMessages(contact));
    }

    public void addMessage(Message message) {
        this.contactWIthMessagesMap.get(message.getContactId()).messages.add(message);

        contactDao.insertMessage(message);
    }
}
