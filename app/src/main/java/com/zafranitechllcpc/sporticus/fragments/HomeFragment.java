package com.zafranitechllcpc.sporticus.fragments;

import android.os.Bundle;

public class HomeFragment extends BaseFragment {

    public static HomeFragment getInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }



}
