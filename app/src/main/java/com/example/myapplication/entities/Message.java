package com.example.myapplication.entities;


import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Integer Id;
    private String Content;
    private Boolean Sent;
    private String Created;
    private String contactId;

    public Message() {
    }

    public Message(String content, String contactId) {
        Content = content;
        this.contactId = contactId;
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