package com.example.myapplication.entities;

public class addContactRequest {
    private String id;
    private String name;
    private String server;

    public addContactRequest(String idToInvite, String nickname, String server) {
        this.id = idToInvite;
        this.name = nickname;
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
}
