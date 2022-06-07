package com.example.myapplication.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;


public class ContactWIthMessages {
    @Embedded public Contact contact;
    @Relation(
            parentColumn = "Id",
            entityColumn = "contactId"
    )
    public List<Message> messages;
}
