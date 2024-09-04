package com.example.mychat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychat.R;
import com.example.mychat.model.ChatmessageModel;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Chat_recycler_Adapter extends FirestoreRecyclerAdapter<ChatmessageModel, Chat_recycler_Adapter.Chatmodelviewholder> {

    Context context;


    public Chat_recycler_Adapter(@NonNull FirestoreRecyclerOptions<ChatmessageModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Chatmodelviewholder holder, int position, @NonNull ChatmessageModel model) {

        if(model.getSenderID().equals(Firebaseutil.currentUserId())){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightText.setText(model.getMessage());
        }
        else{
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftText.setText(model.getMessage());
        }

    }

    @NonNull
    @Override
    public Chatmodelviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row,parent,false);
        return new Chatmodelviewholder(view);
    }

    class Chatmodelviewholder extends RecyclerView.ViewHolder{

        TextView leftText,rightText;
        LinearLayout leftLayout,rightLayout;


        public Chatmodelviewholder(@NonNull View itemView) {
            super(itemView);

            leftText=itemView.findViewById(R.id.left_chat_textview);
            rightText=itemView.findViewById(R.id.right_chat_textview);
            leftLayout=itemView.findViewById(R.id.left_chat_layout);
            rightLayout=itemView.findViewById(R.id.right_chat_layout);



        }
    }
}
