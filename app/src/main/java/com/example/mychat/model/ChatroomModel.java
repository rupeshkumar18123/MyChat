package com.example.mychat.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel {
    String chatroomid;
    List<String> userIds;
    Timestamp lastmessageTimestamp;
    String lastmessageSenderId;

    String lastmessage;

    public ChatroomModel() {
    }

    public ChatroomModel(String chatroomid, List<String> userIds, Timestamp lastmessageTimestamp, String lastmessageSenderId) {
        this.chatroomid = chatroomid;
        this.userIds = userIds;
        this.lastmessageTimestamp = lastmessageTimestamp;
        this.lastmessageSenderId = lastmessageSenderId;

    }

    public String getChatroomid() {
        return chatroomid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public void setChatroomid(String chatroomid) {
        this.chatroomid = chatroomid;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastmessageTimestamp() {
        return lastmessageTimestamp;
    }

    public void setLastmessageTimestamp(Timestamp lastmessageTimestamp) {
        this.lastmessageTimestamp = lastmessageTimestamp;
    }

    public String getLastmessageSenderId() {
        return lastmessageSenderId;
    }

    public void setLastmessageSenderId(String lastmessageSenderId) {
        this.lastmessageSenderId = lastmessageSenderId;
    }
}
