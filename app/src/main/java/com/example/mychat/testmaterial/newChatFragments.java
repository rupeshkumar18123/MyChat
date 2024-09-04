package com.example.mychat.testmaterial;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mychat.Adapter.RecentChatAdapter;
import com.example.mychat.R;
import com.example.mychat.model.ChatroomModel;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class newChatFragments extends Fragment {
    RecyclerView recyclerView;
    RecentChatAdapter adapter;

    public newChatFragments() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_new_chat_fragments, container, false);
       recyclerView=view.findViewById(R.id.newChatRecycler);

       setupNewchatRecycler();
        return view;
    }
    public void setupNewchatRecycler(){
        Query query= Firebaseutil.allchatroomcollectionReference()
                .whereArrayContains("userIds",Firebaseutil.currentUserId())
                .orderBy("lastmessageTimestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatroomModel> options=new FirestoreRecyclerOptions.Builder<ChatroomModel>()
                .setQuery(query,ChatroomModel.class).build();

        adapter=new RecentChatAdapter(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}