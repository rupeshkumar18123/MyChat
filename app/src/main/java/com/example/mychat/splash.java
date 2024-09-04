package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mychat.login.login_screen;
import com.example.mychat.utils.Firebaseutil;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Firebaseutil.islogedin()){
                    startActivity(new Intent(splash.this, MainActivity.class));
                }else {
                    startActivity(new Intent(splash.this, login_screen.class));
                }
                finish();
            }
        },1000);

    }
}