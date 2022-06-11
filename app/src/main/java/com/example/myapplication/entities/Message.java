package com.example.myapplication.entities;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Integer Id;
    private String Content;
    // TODO: change this value to be dynamic
    private Boolean Sent = true;
    private String Created;
    private String contactId;

    public Message() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Message(String content, String contactId) {
        Content = content;
        this.contactId = contactId;
        this.Created = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Boolean getSent() {
        return Sent;
    }

    public void setSent(Boolean sent) {
        Sent = sent;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }
}