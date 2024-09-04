package com.example.mychat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Firebaseutil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;


public class profileFragments extends Fragment {
    EditText username;
    EditText userPhonenumber;
    ProgressBar progressBar;
    Button updateBtn;
    ImageView profileimage;
    TextView logout;

    Usermodel current_user_model;


    public profileFragments() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        username = view.findViewById(R.id.Profile_user_name);
        userPhonenumber = view.findViewById(R.id.profile_phone_number);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        updateBtn = view.findViewById(R.id.profile_update_button);
        profileimage = view.findViewById(R.id.profile_photo);
        logout = view.findViewById(R.id.logout);

        getUserData();
        updateBtn.setOnClickListener((V) -> {
            setUpdateUsername();

        });

        logout.setOnClickListener((v) -> {
            Firebaseutil.logout();
            Intent intent = new Intent(getContext(), splash.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        });


        return view;
    }

    void setUpdateUsername() {

        String newusername = username.getText().toString();

        if (newusername.isEmpty() || newusername.length() < 3) {
            username.setError("username length should not less 3");
            return;
        }
        current_user_model.setUsername(newusername);
        setinprogress(true);
        updateToFirestore();
    }

    void updateToFirestore() {

        Firebaseutil.currentUserDetail().set(current_user_model)
                .addOnCompleteListener((task) -> {
                    setinprogress(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Updated failed !!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getUserData() {
        Firebaseutil.currentUserDetail().get().addOnCompleteListener((task) -> {
            setinprogress(true);

            current_user_model = task.getResult().toObject(Usermodel.class);
            setinprogress(false);
            username.setText(current_user_model.getUsername());
            userPhonenumber.setText(current_user_model.getPhone());
        });

    }

    void setinprogress(Boolean inprogress) {
        if (inprogress) {
            progressBar.setVisibility(View.VISIBLE);
            updateBtn.setVisibility(View.GONE);

        } else {
            progressBar.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
        }
    }


//    @Override
//    public void onStop() {
//        super.onStop();
//        if ((MainActivity.class).getClass().equals(profileFragments.class)){
//            Intent intent = new Intent(getContext(), splash.class);
//            startActivity(intent);
//        }
//
//    }
}


