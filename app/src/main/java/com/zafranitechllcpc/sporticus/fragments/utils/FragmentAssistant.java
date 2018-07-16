package com.zafranitechllcpc.sporticus.fragments.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.zafranitechllcpc.sporticus.R;
import com.zafranitechllcpc.sporticus.fragments.BaseFragment;

public class FragmentAssistant {
    private AppCompatActivity activity;

    public FragmentAssistant(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void changeFragment(int containerViewId, Fragment newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(containerViewId, newFragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(newFragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public Fragment getActiveActionFragment() {
        return activity.getSupportFragmentManager().findFragmentById(R.id.action_container);
    }
}
