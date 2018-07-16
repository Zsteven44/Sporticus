package com.zafranitechllcpc.sporticus.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zafranitechllcpc.sporticus.database.entity.EventEntity;

import java.util.List;

public class DatabaseAdapter {
    private final String TAG = getClass().getSimpleName();

    public void addEvent(@NonNull final AppDatabase db,
                         @NonNull final EventEntity event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.eventDao().insertAll(event);
                Log.e(TAG, TAG + " has added event: " + event.toString());
            }
        });
        thread.start();
    }

    public List<EventEntity> getAllEvents(@NonNull final AppDatabase db){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                List<EventEntity> events = db.eventDao().getAllEvents();
            }
        });

        return null;
    }


}
