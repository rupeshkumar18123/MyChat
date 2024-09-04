package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mychat.Adapter.Search_recycler_adapter;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class search_bar_activity extends AppCompatActivity {

    ImageView backbtn,searchBarBtn;
    EditText userinput;
    RecyclerView recyclerView_search;
    Search_recycler_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        recyclerView_search=findViewById(R.id.search_recycler);
        userinput=findViewById(R.id.search_here_text);
        backbtn=findViewById(R.id.back_btn);
        searchBarBtn=findViewById(R.id.search_bar_btn);

        userinput.requestFocus();
        backbtn.setOnClickListener((v)->{
            Intent intent=new Intent(search_bar_activity.this,MainActivity.class);
            startActivity(intent);
        });

        searchBarBtn.setOnClickListener((v)->{
            String searchinput=userinput.getText().toString();
            if(searchinput.isEmpty() || searchinput.length()<3){
                userinput.setError("invalid user name");
                return;
            }
            setUpSearchRecyclerview(searchinput);
        });


    }
    void setUpSearchRecyclerview(String searchinput){

        Query query= Firebaseutil.allUserCollection()
                .whereGreaterThanOrEqualTo("username",searchinput);

        FirestoreRecyclerOptions<Usermodel> options=new FirestoreRecyclerOptions.Builder<Usermodel>()
                .setQuery(query,Usermodel.class).build();

        adapter= new Search_recycler_adapter(options,getApplicationContext());
        recyclerView_search.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_search.setAdapter(adapter);
        adapter.startListening();
    }
//
//    public search_bar_activity() {
//        super();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.startListening();
        }
    }
}