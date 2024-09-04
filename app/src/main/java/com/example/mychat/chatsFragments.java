package com.example.mychat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mychat.Adapter.RecentChatAdapter;
import com.example.mychat.Adapter.Search_recycler_adapter;
import com.example.mychat.model.ChatroomModel;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;


public class chatsFragments extends Fragment {

    RecyclerView recyclerView;
    RecentChatAdapter adapter;


    public chatsFragments() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_chats, container, false);
      recyclerView=view.findViewById(R.id.recycler_chat_fragments);

      setupRecyclerView();
        return view;
    }
    void setupRecyclerView(){
        Query query= Firebaseutil.allchatroomcollectionReference()
                .whereArrayContains("userIds",Firebaseutil.currentUserId())
                .orderBy("lastmessageTimestamp",Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<ChatroomModel> options=new FirestoreRecyclerOptions.Builder<ChatroomModel>()
                .setQuery(query,ChatroomModel.class).build();

        adapter= new RecentChatAdapter(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}