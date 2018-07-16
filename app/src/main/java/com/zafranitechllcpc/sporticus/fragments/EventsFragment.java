package com.zafranitechllcpc.sporticus.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zafranitechllcpc.sporticus.R;
import com.zafranitechllcpc.sporticus.activities.BaseActivity;
import com.zafranitechllcpc.sporticus.adapters.EventsListAdapter;
import com.zafranitechllcpc.sporticus.database.entity.EventEntity;
import com.zafranitechllcpc.sporticus.viewmodels.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends BaseFragment implements View.OnLongClickListener{
    private final String TAG = getClass().getSimpleName();
    private BaseActivity activity;
    private EventViewModel eventViewModel;
    private EventsListAdapter eventsListAdapter;
    private RecyclerView recyclerView;

    public static EventsFragment getInstance() {
        Bundle args = new Bundle();
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (BaseActivity) getActivity();
        Log.e(TAG, TAG + " onViewCreated");
        Toast.makeText(activity, "I am " + TAG, Toast.LENGTH_SHORT).show();

        // Initialize Events RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        eventsListAdapter = new EventsListAdapter(new ArrayList<EventEntity>(), this);
        recyclerView.setAdapter(eventsListAdapter);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(EventsFragment.this, new Observer<List<EventEntity>>() {
            @Override
            public void onChanged(@Nullable List<EventEntity> eventEntities) {
                eventsListAdapter.updateItems(eventEntities);
            }
        });
    }


    @Override
    public boolean onLongClick(View v) {
        EventEntity eventEntity = (EventEntity) v.getTag();
        eventViewModel.deleteEvents(eventEntity);
        return false;
    }
}
