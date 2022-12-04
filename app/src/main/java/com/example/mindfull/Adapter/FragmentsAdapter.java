package com.example.mindfull.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mindfull.Fragments.AlarmFragment;
import com.example.mindfull.Fragments.ExerciseFragment;
import com.example.mindfull.Fragments.HomeFragment;
import com.example.mindfull.Fragments.MeditateFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0: return new HomeFragment();
            case 1: return new MeditateFragment();
            case 2: return new ExerciseFragment();
            case 3: return new AlarmFragment();
            default: return new HomeFragment();

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if(position==0) {
            title = "HOME";
        }
        if(position==1) {
            title = "MEDITATE";
        }
        if(position==2) {
            title = "EXERCISE";
        }
        if(position==3) {
            title = "SLEEP";
        }

        return title;
    }
}
