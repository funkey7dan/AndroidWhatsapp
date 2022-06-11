package com.example.myapplication.API;

public class MessageRequest {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageRequest(String content) {
        this.content = content;
    }

    String content;

}
