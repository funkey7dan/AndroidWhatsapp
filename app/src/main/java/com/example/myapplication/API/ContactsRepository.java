package com.example.myapplication.API;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;
import com.example.myapplication.utils.DataSingleton;

import java.util.List;

public class ContactsRepository {
    DataSingleton data = DataSingleton.getInstance();
    private ContactDao contactDao;
    private AppDB db;
    private ContactsAPI contactsAPI;
    private MessagesAPI messagesAPI;

    public ContactsRepository(Context applicationContext) {
        db = Room.databaseBuilder(applicationContext, AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        contactsAPI = new ContactsAPI(contactDao);
        messagesAPI = new MessagesAPI(contactDao);
    }

    public LiveData<List<Contact>> getAllContact() {
        contactsAPI.get();
        return contactDao.getContacts();
    }

    public LiveData<ContactWIthMessages> getAllMessages() {
        messagesAPI.get(data.getActiveContact());
        return contactDao.getChatWith(data.getActiveContact());
    }

    public void addContact(Contact contact) {
        // TODO: think about it -
        // I don't think its ok to add it to room immediately
//        contactDao.insertSingleContact(contact);
        // post
        contactsAPI.addContact(contact);
        // TODO: change immediately the logic! we invite only
        // if the add contact is valid (200)
//        contactsAPI.inviteContact(contact);
    }

    public void addMessage(Message message) {
        contactDao.insertSingleMessage(message);
        messagesAPI.post(data.getActiveContact(), message.getContent());
        messagesAPI.transfer(data.getActiveContact(), data.getUser(), message.getContent());
    }

    public void sendToken() {
        contactsAPI.sendToken(data.getToken());
    }

    public void editContact(String content) {

    }
}
