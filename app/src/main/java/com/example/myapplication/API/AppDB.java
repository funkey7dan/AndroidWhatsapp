package com.example.myapplication.API;

import androidx.room.Database;

import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.Contact;

import androidx.room.RoomDatabase;

@Database(entities = {Message.class,Contact.class},version = 1)
public abstract class AppDB extends RoomDatabase{
    public abstract ContactDao contactDao();



}
