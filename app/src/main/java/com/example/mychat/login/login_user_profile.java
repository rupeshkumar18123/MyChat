package com.example.mychat.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mychat.MainActivity;
import com.example.mychat.R;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Firebaseutil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;


public class login_user_profile extends AppCompatActivity {

    EditText username;
    Button letmeinbtn;
    ProgressBar pb;
    String userPhoneNumber;
    Usermodel usermodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_profile);

        username=findViewById(R.id.user_name_text);
        letmeinbtn=findViewById(R.id.let_me_in_btn);
        pb=findViewById(R.id.progress_user_profile);
        //userPhoneNumber=getIntent().getExtras().toString("phonenumber");
        userPhoneNumber = getIntent().getExtras().getString("phonenumber");
        getusername();
        letmeinbtn.setOnClickListener((v)->{
            setusername();
        });

    }

    void setusername(){
        String user=username.getText().toString();

        if (user.isEmpty() || user.length()<3){
            username.setError("username length should not less 3");
            return;
        }

        setinprogress(true);
        if (usermodel!=null){
            usermodel.setUsername(user);
        }
        else {
         usermodel=new Usermodel(userPhoneNumber,user, Timestamp.now(),Firebaseutil.currentUserId());
        }
        Firebaseutil.currentUserDetail().set(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setinprogress(false);
                if(task.isSuccessful()){
                    Intent intent =new Intent(login_user_profile.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });


    }

    void getusername(){
        setinprogress(true);
        Firebaseutil.currentUserDetail().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setinprogress(false);
                if (task.isSuccessful()) {

                    usermodel = task.getResult().toObject(Usermodel.class);

                    if (usermodel != null) {
                    username.setText(usermodel.getUsername());
                    }
                }
            }
        });
    }
    void setinprogress(Boolean inprogress){
        if(inprogress){
            pb.setVisibility(View.VISIBLE);
            letmeinbtn.setVisibility(View.GONE);

        }
        else {
            pb.setVisibility(View.GONE);
            letmeinbtn.setVisibility(View.VISIBLE);
        }
    }
}