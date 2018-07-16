package com.zafranitechllcpc.sporticus.fragments;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zafranitechllcpc.sporticus.R;
import com.zafranitechllcpc.sporticus.activities.BaseActivity;
import com.zafranitechllcpc.sporticus.activities.MainActivity;

public class NewsFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private BaseActivity activity;

    public static NewsFragment getInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (BaseActivity) getActivity();
        Log.e(TAG, TAG + " onViewCreated");
        Toast.makeText(activity, "I am " + TAG, Toast.LENGTH_SHORT).show();
    }

}
