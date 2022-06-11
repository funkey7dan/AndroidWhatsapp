package com.example.myapplication.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;


public class ContactWIthMessages {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "id",
            entityColumn = "contactId"
    )
    public List<Message> messages = new ArrayList<>();
}
