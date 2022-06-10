package com.example.myapplication.API;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;
import java.util.List;

import androidx.room.Transaction;

@Dao
public interface ContactDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingle(Contact contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Contact> contacts);

    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> getContacts();

    // here we get a pair of <contact, messages>
    @Transaction
    @Query("SELECT * FROM contact WHERE contact.id = :contactId")
    LiveData<ContactWIthMessages> getChatWith(String contactId);

}


