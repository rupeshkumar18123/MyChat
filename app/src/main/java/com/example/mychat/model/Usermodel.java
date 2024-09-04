package com.example.mychat.model;

import com.google.firebase.Timestamp;

public class Usermodel {
    private String phone;
    private String username;
    private Timestamp createdtimestamp;
    private String userId;

    public Usermodel() {
    }

    public Usermodel(String phone, String username, Timestamp createdtimestamp,String userId) {
        this.phone = phone;
        this.username = username;
        this.createdtimestamp = createdtimestamp;
        this.userId=userId;
    }
    public Usermodel(String phone, String username, Timestamp createdtimestamp) {
        this.phone = phone;
        this.username = username;
        this.createdtimestamp = createdtimestamp;

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public  String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedtimestamp() {
        return createdtimestamp;
    }

    public void setCreatedtimestamp(Timestamp createdtimestamp) {
        this.createdtimestamp = createdtimestamp;
    }
}
