package com.example.myapplication.API;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;

import java.util.List;

public class ContactsRepository {
    DataSingleton data = DataSingleton.getInstance();
    private ContactDao contactDao;
    //private ContactListData contactListData;
    private AppDB db;

    public ContactsRepository(Context applicationContext) {
        db = Room.databaseBuilder(applicationContext, AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        //contactListData = new ContactListData();
    }

//    class ContactListData extends MutableLiveData<List<Contact>> {
//        public ContactListData() {
//            super();
//            // TODO: changed this, check if everything works. thread maybe needs to do it?
//            List<Contact> contacts = contactDao.getContacts();
//            setValue(contacts);
//        }
//
//        @Override
//        protected void onActive() {
//            super.onActive();
//
//            new Thread(() -> {
//                contactListData.postValue(contactDao.getContacts());
//            }).start();
//        }
//    }

    public LiveData<List<Contact>> getAll() {
        return contactDao.getContacts();
    }

    public LiveData<ContactWIthMessages> getAllMessages() {
        return contactDao.getChatWith(data.getActiveContact());
    }

    public void add(Contact contact) {
        contactDao.insertSingle(contact);
        // TODO: don't think its optimal, maybe the dao will get LiveData and set it there?
//        contactListData.setValue(contactDao.getContacts());
    }

    public void addMessage(Message message) {
        contactDao.insertMessage(message);
    }
}
