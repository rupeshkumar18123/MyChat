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
import com.example.mychat.model.ChatroomModel;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Androidutil;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecentChatAdapter extends FirestoreRecyclerAdapter<ChatroomModel, RecentChatAdapter.chatmodelViewholder> {
    Context context;
    public RecentChatAdapter(@NonNull FirestoreRecyclerOptions<ChatroomModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull chatmodelViewholder holder, int position, @NonNull ChatroomModel model) {
        Firebaseutil.getOtherUserFromChatRoom(model.getUserIds())
                .get().addOnCompleteListener((task)->{
                   if (task.isSuccessful()){
                       Boolean lastmessageSenderidme=Firebaseutil.currentUserId().equals(model.getLastmessageSenderId());
                       Usermodel Otherusermodel=task.getResult().toObject(Usermodel.class);

                       if (lastmessageSenderidme)
                           holder.lastmessage.setText("you: "+model.getLastmessage());
                       else
                           holder.lastmessage.setText(model.getLastmessage());

                       holder.username.setText(Otherusermodel.getUsername());

                       holder.lastsendTime.setText(Firebaseutil.timestamptoString(model.getLastmessageTimestamp()));
                       holder.itemView.setOnClickListener((v)->{

                           Intent intent =new Intent(context, chat_room_.class);
                           Androidutil.passUsermodelAsIntent(intent,Otherusermodel);
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       });

                   }
                });

    }

    @NonNull
    @Override
    public chatmodelViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_fragment_recycler_row,parent,false);
        return new chatmodelViewholder(view);
    }

    public  class chatmodelViewholder extends RecyclerView.ViewHolder{
        TextView username;
        TextView lastmessage;
        ImageView userprofile;
        TextView lastsendTime;

        public chatmodelViewholder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.otherUsername);
            lastmessage=itemView.findViewById(R.id.messagetxt);
            lastsendTime=itemView.findViewById(R.id.timestamp);
        }
    }
}
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.mychat.R;
//import com.example.mychat.model.ChatroomModel;
//import com.example.mychat.model.Usermodel;
//import com.example.mychat.utils.Firebaseutil;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.DocumentSnapshot;
//
//public class RecentChatAdapter extends FirestoreRecyclerAdapter<ChatroomModel, RecentChatAdapter.chatmodelviewholder> {
//
//    Context context;
//
//
//    public RecentChatAdapter(@NonNull FirestoreRecyclerOptions<ChatroomModel> options, Context context) {
//        super(options);
//        this.context=context;
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull chatmodelviewholder holder, int position, @NonNull ChatroomModel model) {
//
//
//        Firebaseutil.getOtherUserFromChatRoom(model.getUserIds())
//                .get().addOnCompleteListener(task -> {
//                    if (task.isSuccessful()){
//                        Usermodel otherusermodel=task.getResult().toObject(Usermodel.class);
//                        holder.username.setText(otherusermodel.getUsername());
//                        holder.lastmessage.setText(model.getLastmessage());
//                      //  holder.lastsendTime.setText(Firebaseutil.timestamptoString(model.getLastmessageTimestamp()));
//                    }
//                });
//    }
//
//    @NonNull
//    @Override
//    public chatmodelviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(context).inflate(R.layout.search_user_view,parent,false);
//        return new chatmodelviewholder(view);
//    }
//
//    class chatmodelviewholder extends RecyclerView.ViewHolder{
//
//        TextView username;
//        TextView lastmessage;
//        ImageView userprofile;
//        TextView lastsendTime;
//        public chatmodelviewholder(@NonNull View itemView) {
//            super(itemView);
//
//            username=itemView.findViewById(R.id.user_name_text);
//            lastmessage=itemView.findViewById(R.id.user_phone_text);
//
////            username=itemView.findViewById(R.id.otherUsername);
////            lastmessage=itemView.findViewById(R.id.messagetxt);
////            lastsendTime= itemView.findViewById(R.id.timestamp);
//
//
//
//        }
//    }
//}
//
