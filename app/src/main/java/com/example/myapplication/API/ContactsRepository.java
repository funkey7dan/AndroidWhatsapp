package com.example.myapplication.API;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.DataSingleton;
import com.example.myapplication.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    DataSingleton data = DataSingleton.getInstance();
    private ContactDao contactDao;
    private ContactListData contactListData;
    private AppDB db;

    public ContactsRepository(Context applicationContext) {
        db = Room.databaseBuilder(applicationContext, AppDB.class, data.getUser()+"_db").allowMainThreadQueries().build();
        contactDao = db.contactDao();
        contactListData = new ContactListData();
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            // TODO: changed this, check if everything works. thread maybe needs to do it?
            List<Contact> contacts = contactDao.getContacts();
            setValue(contacts);
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                contactListData.postValue(contactDao.getContacts());
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(Contact contact) {
        contactDao.insertSingle(contact);
        // TODO: don't think its optimal, maybe the dao will get LiveData and set it there?
        contactListData.setValue(contactDao.getContacts());
    }
}
