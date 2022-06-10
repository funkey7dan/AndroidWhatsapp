package com.example.myapplication.utils;

public class DataSingleton {
    private String user = null;
    private String activeContact = null;
    private String token = null;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter/setter
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActiveContact() {
        return activeContact;
    }

    public void setActiveContact(String activeContact) {
        this.activeContact = activeContact;
    }

    private static DataSingleton instance;

    public static DataSingleton getInstance() {
        if (instance == null)
            instance = new DataSingleton();
        return instance;
    }

    private DataSingleton() { }
}