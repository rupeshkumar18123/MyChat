package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mychat.Adapter.FragmentadderAdapter;
import com.example.mychat.testmaterial.newChatFragments;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

   // BottomNavigationView bottomnavigation;

    TabLayout bottomtablayout;

    chatsFragments chat;
    profileFragments profile;

   // newChatFragments newChatFragment;
    ViewPager viewPager;

    ImageView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat =new chatsFragments();
        profile=new profileFragments();
     //   newChatFragment=new newChatFragments();
       // bottomnavigation=findViewById(R.id.bottomnavigation);
        bottomtablayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        searchBtn=findViewById(R.id.searchbtn);

//        bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId()==R.id.menu_chats){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,chat).commit();
//                }
//                if (item.getItemId()==R.id.menu_profile){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,profile).commit();
//                }
//                return false;
//            }
//        });
//        bottomnavigation.setSelectedItemId(R.id.menu_chats);

        FragmentadderAdapter fradap=new FragmentadderAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fradap.addfrg(chat,"chats",R.drawable.baseline_chat_24);
        fradap.addfrg(profile,"profile",R.drawable.person_24);
        //fradap.addfrg(newChatFragment,"newchat",R.drawable.baseline_chat_24);

        viewPager.setAdapter(fradap);
        bottomtablayout.setupWithViewPager(viewPager);

        bottomtablayout.getTabAt(0).setIcon(fradap.getTabIcon(0));

        bottomtablayout.getTabAt(1).setIcon(fradap.getTabIcon(1));
       // bottomtablayout.getTabAt(2).setIcon(fradap.getTabIcon(2));

        searchBtn.setOnClickListener((v)->{
            Intent intent=new Intent(MainActivity.this, search_bar_activity.class);
            startActivity(intent);
        });

    }
}