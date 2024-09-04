package com.example.mychat.model;

import com.google.firebase.Timestamp;

public class ChatmessageModel {
    private String message;
    private String senderID;
    private Timestamp timestamp;
    public ChatmessageModel() {
    }

    public ChatmessageModel(String message, String senderID, Timestamp timestamp) {
        this.message = message;
        this.senderID = senderID;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
