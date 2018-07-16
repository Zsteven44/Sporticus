package com.zafranitechllcpc.sporticus.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zafranitechllcpc.sporticus.database.AppDatabase;
import com.zafranitechllcpc.sporticus.database.entity.EventEntity;

import java.util.Arrays;
import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private final String TAG = getClass().getSimpleName();
    private AppDatabase appDatabase;
    private LiveData<List<EventEntity>> eventList;

    public EventViewModel(@NonNull Application application) {
        super(application);
        this.appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        eventList = appDatabase.eventDao().getAllEvents();
    }

    public LiveData<List<EventEntity>> getAllEvents() {
        return eventList;
    }

    public void deleteEvents(EventEntity... events) {
        new deleteAsyncTask(appDatabase).execute(events);
    }

    public void insertEvents(EventEntity... events) {
        new insertAsyncTask(appDatabase).execute(events);
    }

    private static class deleteAsyncTask extends AsyncTask<EventEntity, Void, Void> {
        private final String TAG = getClass().getSimpleName();
        private AppDatabase appDatabase;

        public deleteAsyncTask(AppDatabase appDatabase) {
            this.appDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(EventEntity... eventEntities) {
            appDatabase.eventDao().deleteEvents(eventEntities);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<EventEntity, Void, Void> {
        private final String TAG = getClass().getSimpleName();
        private AppDatabase appDatabase;

        public insertAsyncTask(AppDatabase appDatabase) {
            this.appDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(EventEntity... eventEntities) {
            appDatabase.eventDao().insertAll(eventEntities);
            Log.e(TAG,"Inserting "+ Arrays.toString(eventEntities));
            return null;
        }
    }

}
