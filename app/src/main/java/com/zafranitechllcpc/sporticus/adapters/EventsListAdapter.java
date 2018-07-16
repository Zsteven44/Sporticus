package com.zafranitechllcpc.sporticus.adapters;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zafranitechllcpc.sporticus.R;
import com.zafranitechllcpc.sporticus.database.entity.EventEntity;
import com.zafranitechllcpc.sporticus.databinding.RecyclerItemEventlistBinding;

import java.util.List;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.EventViewHolder> {
    private List<EventEntity> eventList;
    private View.OnLongClickListener longClickListener;

    public EventsListAdapter(final List<EventEntity> eventList,
                             final View.OnLongClickListener longClickListener) {
        this.eventList = eventList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerItemEventlistBinding binding = RecyclerItemEventlistBinding.inflate(layoutInflater, parent, false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventEntity eventEntity = eventList.get(position);
        holder.bind(eventEntity);
    }

    public void updateItems(List<EventEntity> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerItemEventlistBinding binding;

        EventViewHolder(RecyclerItemEventlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(EventEntity eventEntity) {
            binding.setEvent(eventEntity);
            binding.executePendingBindings();
        }

    }
}
