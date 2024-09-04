package com.example.mychat.Adapter;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mychat.R;

import java.util.ArrayList;

public class FragmentadderAdapter extends FragmentPagerAdapter {

   private ArrayList<Fragment> arrfrg=new ArrayList<>();
    private ArrayList<String> arrstr=new ArrayList<>();
    private ArrayList<Integer> arrdraw=new ArrayList<>();

    public FragmentadderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrfrg.get(position);
    }

    @Override
    public int getCount() {
        return arrfrg.size();
    }
    public void addfrg(Fragment frg,String str,int draw){
        arrfrg.add(frg);
        arrstr.add(str);
        arrdraw.add(draw);
    }
    public void addfrg(Fragment frg,String str){
        arrfrg.add(frg);
        arrstr.add(str);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrstr.get(position);
    }

    public int getTabIcon(int position) {
        return arrdraw.get(position);

    }

}
