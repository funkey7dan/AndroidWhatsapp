package com.example.myapplication.entities;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;


public class ContactWIthMessages {
    @Embedded public Contact contact;
    @Relation(
            parentColumn = "Id",
            entityColumn = "Id"
    )
    public List<Message> messages;
}
