package com.example.mychat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychat.R;
import com.example.mychat.chat_room_;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Androidutil;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Search_recycler_adapter extends FirestoreRecyclerAdapter<Usermodel, Search_recycler_adapter.Usermodelviewholder> {

    Context context;


    public Search_recycler_adapter(@NonNull FirestoreRecyclerOptions<Usermodel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Usermodelviewholder holder, int position, @NonNull Usermodel model) {
        holder.username.setText(model.getUsername());
        holder.usernumber.setText(model.getPhone());
        if(model.getUserId().equals(Firebaseutil.currentUserId())){
            holder.username.setText(model.getUsername()+"(me)");
        }
        holder.itemView.setOnClickListener((v)->{

            Intent intent =new Intent(context, chat_room_.class);
            Androidutil.passUsermodelAsIntent(intent,model);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public Usermodelviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_user_view,parent,false);
        return new Usermodelviewholder(view);
    }

    class Usermodelviewholder extends RecyclerView.ViewHolder{

        TextView username;
        TextView usernumber;
        ImageView userprofile;
        public Usermodelviewholder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.user_name_text);
            usernumber=itemView.findViewById(R.id.user_phone_text);



        }
    }
}
