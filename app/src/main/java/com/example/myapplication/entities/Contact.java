package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;

@Entity
// a class for the the contact we're chatting with
public class Contact {
    @PrimaryKey(autoGenerate = false)
    // contact name
    @NonNull
    private String Id;
    // contact nickname
    private String Name;
    private String Server;
    // the contents of the last message with this contact
    private String Last;
    private String lastdate;

    public Contact() {
    }

    public Contact(@NonNull String id, String name, String server) {
        Id = id;
        Name = name;
        Server = server;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public String getLast() {
        return Last;
    }

    public void setLast(String last) {
        Last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
}

