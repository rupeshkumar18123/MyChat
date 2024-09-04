
package com.example.mychat.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mychat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class login_otp extends AppCompatActivity {
    String user_phone_number;

    EditText mobileotp;
    Button btnxt;
    ProgressBar pb;
    TextView resendOTPtext;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    Long timeout=30L;
    String verificationotp;
    PhoneAuthProvider.ForceResendingToken frt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        mobileotp=findViewById(R.id.enter_otp);
        btnxt=findViewById(R.id.otp_verify_btn);
        pb=findViewById(R.id.progress_otp);
        resendOTPtext=findViewById(R.id.resend_text_otp);


        user_phone_number=getIntent().getExtras().getString("phone");
       // Toast.makeText(this,"OTP sent to"+ user_phone_number, Toast.LENGTH_LONG).show();

        sendOtp(user_phone_number,false);

        btnxt.setOnClickListener((v)->{
            String Enteredtop=mobileotp.getText().toString();
            PhoneAuthCredential mauth=PhoneAuthProvider.getCredential(verificationotp,Enteredtop);
            SingIN(mauth);
            setinprogress(true);
        });

        resendOTPtext.setOnClickListener((view) -> {
            sendOtp(user_phone_number,true);
        });

    }


    //method used to send otp only
   void sendOtp(String phonenumber, Boolean isResend){
        startResendTimer();
        setinprogress(true);
       PhoneAuthOptions.Builder builder=
               PhoneAuthOptions.newBuilder(auth)
                       .setPhoneNumber(user_phone_number)
                       .setTimeout(timeout, TimeUnit.SECONDS)
                       .setActivity(this)
                       .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                           @Override
                           public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                               SingIN(phoneAuthCredential);
                               setinprogress(false);

                           }

                           @Override
                           public void onVerificationFailed(@NonNull FirebaseException e) {
                               Toast.makeText(login_otp.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                               setinprogress(false);
                           }

                           @Override
                           public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               super.onCodeSent(s, forceResendingToken);
                               frt=forceResendingToken;
                               verificationotp=s;
                               Toast.makeText(login_otp.this, "otp sent succesfully", Toast.LENGTH_SHORT).show();
                               setinprogress(false);
                           }
                       });
       if(isResend){

           PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(frt).build());
       }
       else{
           PhoneAuthProvider.verifyPhoneNumber(builder.build());
       }



    }

    void setinprogress(Boolean inprogress){
        if(inprogress){
            pb.setVisibility(View.VISIBLE);
            btnxt.setVisibility(View.GONE);

        }
        else {
            pb.setVisibility(View.GONE);
            btnxt.setVisibility(View.VISIBLE);
        }
    }

    void SingIN(PhoneAuthCredential phoneAuthCredential){
        setinprogress(true);
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setinprogress(false);
                if(task.isSuccessful()){
                    Intent intent =new Intent(login_otp.this,login_user_profile.class);
                    intent.putExtra("phonenumber",user_phone_number);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(login_otp.this, "otp verification failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void startResendTimer(){
        resendOTPtext.setEnabled(false);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
           timeout--;
           resendOTPtext.setText("resend OTP in"+timeout+"seconds");
           if(timeout<=0){
               timeout=30L;
               timer.cancel();
               runOnUiThread(() -> {
                   resendOTPtext.setEnabled(true);
               });

           }
            }
        },0,1000);
    }

}