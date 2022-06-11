package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
// a class for the the contact we're chatting with
public class Contact {
    @PrimaryKey(autoGenerate = false)
    // contact name
    @NonNull
    private String id;
    // contact nickname
    private String name;
    private String server;
    // the contents of the last message with this contact
    private String last;
    private String lastdate;

//    public Contact() {
//    }

    public Contact(@NonNull String id, String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
}

