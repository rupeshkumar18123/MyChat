package com.example.mychat.utils;

import android.annotation.SuppressLint;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class Firebaseutil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean islogedin(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }
    public static DocumentReference currentUserDetail(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static CollectionReference allUserCollection(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getchatRoomReference(String chatRoomId){

        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatRoomId);
    }

    public static String getchatroomId(String userID1,String userID2){
        if(userID1.hashCode()<userID2.hashCode()){
            return userID1+"_"+userID2;
        }
        else {
            return userID2+"_"+userID1;
        }
    }

    public static CollectionReference getChataroomMessageReference(String chatroomid ){
        return getchatRoomReference(chatroomid).collection("chats");
    }
    public static CollectionReference allchatroomcollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }
    public static DocumentReference getOtherUserFromChatRoom(List<String> userIds){
        if(userIds.get(0).equals(Firebaseutil.currentUserId())){
            return allUserCollection().document(userIds.get(1));

        }else{
            return allUserCollection().document(userIds.get(0));
        }
    }

    public static String timestamptoString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
    public static void logout(){
        FirebaseAuth.getInstance().signOut();

    }

}
