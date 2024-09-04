package com.example.mychat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mychat.R;

import com.hbb20.CountryCodePicker;

public class login_screen extends AppCompatActivity {

    EditText phoneinput;
    ProgressBar progressBar;
    Button sendotpbtn;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        phoneinput=findViewById(R.id.user_mobile_number);
        progressBar=findViewById(R.id.progress_login);
        sendotpbtn=findViewById(R.id.send_otp_btn);
        ccp=findViewById(R.id.country_code);
        ccp.registerCarrierNumberEditText(phoneinput);

        progressBar.setVisibility(View.GONE);
        sendotpbtn.setOnClickListener((view )-> {


                if(!ccp.isValidFullNumber()){
                    phoneinput.setError("invalid phone number !!");
                    return;
                }
                Intent intent=new Intent(login_screen.this, login_otp.class);
                intent.putExtra("phone",ccp.getFullNumberWithPlus());
                startActivity(intent);


        });

    }
}