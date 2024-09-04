package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mychat.Adapter.Chat_recycler_Adapter;
import com.example.mychat.model.ChatmessageModel;
import com.example.mychat.model.ChatroomModel;
import com.example.mychat.model.Usermodel;
import com.example.mychat.utils.Androidutil;
import com.example.mychat.utils.Firebaseutil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Arrays;
import java.util.Collections;

public class chat_room_ extends AppCompatActivity {

    TextView otherusername;
    EditText writemessagehere;
    ImageView sendMessageBtn;
    ImageView Backbtn;
    RecyclerView chatrecyclerView;
    ChatroomModel chatroommodel;

    Usermodel otherUser;
    String chatRoomId;
    ZegoSendCallInvitationButton videocall,voicecall;
    Chat_recycler_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        otherusername=findViewById(R.id.chat_user_name);

        writemessagehere=findViewById(R.id.messege_input);
        sendMessageBtn=findViewById(R.id.message_send_btn);
        Backbtn=findViewById(R.id.chat_back_btn);
        chatrecyclerView=findViewById(R.id.chat_recycler_view);
        voicecall=findViewById(R.id.voicecallbtn);
        videocall=findViewById(R.id.vdocallbtn);



        otherUser= Androidutil.getUserModelFromIntent(getIntent());
        chatRoomId= Firebaseutil.getchatroomId(Firebaseutil.currentUserId(),otherUser.getUserId());
        Backbtn.setOnClickListener((v)->{
            onBackPressed();
        });

        otherusername.setText(otherUser.getUsername());

        getOrcreateChatRoom();

        sendMessageBtn.setOnClickListener((v)->{
            String message=writemessagehere.getText().toString().trim();
            if(message.isEmpty())
                return;
            sendMessageTOUser(message);
        });


        setupRecyclerView();
//calling starts
        startservice(Firebaseutil.currentUserId());

        setvideocall(otherUser.getUserId());
        setviocecall(otherUser.getUserId());

    }

    void setupRecyclerView(){
        Query query= Firebaseutil.getChataroomMessageReference(chatRoomId)
                .orderBy("timestamp",Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<ChatmessageModel> options=new FirestoreRecyclerOptions.Builder<ChatmessageModel>()
                .setQuery(query,ChatmessageModel.class).build();

        adapter= new Chat_recycler_Adapter(options,getApplicationContext());
        LinearLayoutManager manger=new LinearLayoutManager(this);
        manger.setReverseLayout(true);
       chatrecyclerView.setLayoutManager(manger);
        chatrecyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                chatrecyclerView.smoothScrollToPosition(0);
            }
        });

    }
    public void getOrcreateChatRoom(){
        Firebaseutil.getchatRoomReference(chatRoomId).get().addOnCompleteListener((task -> {

            if(task.isSuccessful()){
                chatroommodel=task.getResult().toObject(ChatroomModel.class);
                if(chatroommodel==null){
                    chatroommodel=new ChatroomModel(
                            chatRoomId,
                            Arrays.asList(Firebaseutil.currentUserId(),otherUser.getUserId()),
                            Timestamp.now(),
                            ""
                    );
                    Firebaseutil.getchatRoomReference(chatRoomId).set(chatroommodel);
                }
            }

        }));

    }

    //sending message
    public void sendMessageTOUser(String message){
        chatroommodel.setLastmessageTimestamp(Timestamp.now());
        chatroommodel.setLastmessageSenderId(Firebaseutil.currentUserId());
        chatroommodel.setLastmessage(message);
        Firebaseutil.getchatRoomReference(chatRoomId).set(chatroommodel);

        ChatmessageModel chatmessageModel=new ChatmessageModel(message,Firebaseutil.currentUserId(),Timestamp.now());

        Firebaseutil.getChataroomMessageReference(chatRoomId).add(chatmessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            writemessagehere.setText("");
                        }
                    }
                });


    }

//calling features
    void startservice(String userID){
        Application application =getApplication() ; // Android's application context
        long appID =1463904883 ;   // yourAppID
        String appSign ="8e94452355333fc8c7d197415c9cce267715f6fde638b924fe1d1217e7ff8060";  // yourAppSign
      // String userID =userId; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName =userID ;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }


    void setviocecall(String targetUserID){
        voicecall.setIsVideoCall(false);
        voicecall.setResourceID("zego_uikit_call");
        voicecall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
    }
    void setvideocall(String targetUserID){
        videocall.setIsVideoCall(true);
        videocall.setResourceID("zego_uikit_call");
        videocall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
    }

//    public static String username ;
//  public static String getusername(){
//      Usermodel currentUser;
//       //String username ;
//
//        currentUser=   Firebaseutil.currentUserDetail().get().getResult().toObject(Usermodel.class);
//
//        if(currentUser!=null) {
//            username = currentUser.getUsername();
//        }
//
////        Firebaseutil.currentUserDetail().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
////            @Override
////            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////
////                if (task.isSuccessful()) {
////
////                    currentUser = task.getResult().toObject(Usermodel.class);
////
////
////                    if(currentUser != null)
////                         username[0] = currentUser.getUsername();
////
////                }
////            }
////        });
//        return  username;
//    }

}