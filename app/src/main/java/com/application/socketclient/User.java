package com.application.socketclient;

public class User {
    private String username;
    private String email;
    private String password;
   private String id;

    public User(String id, String name, String email, String password) {
        this.username = name;
        this.email = email;
        this.id=id;

        this.password = password;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
