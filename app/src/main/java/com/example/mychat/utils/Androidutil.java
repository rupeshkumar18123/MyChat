package com.example.mychat.utils;

import android.content.Intent;

import com.example.mychat.model.Usermodel;

public class Androidutil {
    public static void passUsermodelAsIntent(Intent intent, Usermodel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getUserId());

    }
    public static Usermodel getUserModelFromIntent(Intent intent){
        Usermodel usermodel=new Usermodel();

        usermodel.setUsername(intent.getStringExtra("username"));
        usermodel.setUserId(intent.getStringExtra("userId"));
        usermodel.setPhone(intent.getStringExtra("userphone"));
        return usermodel;

    }
}
