package com.spa.model;

public class User {

    public User(int user_id, String username, Boolean admin) {
        super();
        this.user_id = user_id;
        this.username = username;
        this.admin = admin;
    }

    public User() {
        super();
    }

    private int user_id;
    private String username;
    private Boolean admin;

    public int getId() {
        return this.user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public Boolean getAdmin() {
        return this.admin;
    }

}