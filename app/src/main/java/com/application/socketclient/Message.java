package com.application.socketclient;

public class Message {
    String message;
    String userid;
    String userName;

    public Message(String messageText, String userId, String desId) {
        message = messageText;
        this.userid = userId;
        this.userName = desId;
    }

    public String getMessage() {
        return message;
    }

    public String getUserid() {
        return userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
