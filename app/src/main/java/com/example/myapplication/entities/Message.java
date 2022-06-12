package com.example.myapplication.entities;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Integer id;
    private String content;
    // TODO: change this value to be dynamic
    private String sender;
    private String receiver;
    private String created;
    private String contactId;

    public Message() {
    }

    public Message(@NonNull Integer id, String content,
                   String sender, String receiver, String created) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.created = created;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Message(String content, String contactId) {
        this.content = content;
        this.contactId = contactId;
        this.created = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Message(String content, String contactId, String sender, String receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.contactId = contactId;
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if(time.length()>19){
            this.created = time.substring(0, 19);
        }
        else{
            this.created = time;
        }
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}