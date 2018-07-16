package com.zafranitechllcpc.sporticus.fragments;

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

public class AddEventFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private BaseActivity activity;

    public static AddEventFragment getInstance() {

        Bundle args = new Bundle();
        AddEventFragment fragment = new AddEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addevents, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (BaseActivity) getActivity();
        Log.e(TAG, TAG + " onViewCreated");
        Toast.makeText(activity, "I am " + TAG, Toast.LENGTH_SHORT).show();


    }
}
