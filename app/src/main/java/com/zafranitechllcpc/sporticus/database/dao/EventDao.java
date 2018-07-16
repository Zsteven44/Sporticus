package com.zafranitechllcpc.sporticus.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zafranitechllcpc.sporticus.database.entity.EventEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface EventDao {
    @Query("SELECT * FROM events")
    LiveData<List<EventEntity>> getAllEvents();

    @Query("SELECT * FROM events where uid IN (:userIds)")
    LiveData<List<EventEntity>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM events where event_name LIKE :eventName")
    LiveData<List<EventEntity>> findEventByName(String eventName);

    @Insert(onConflict = REPLACE)
    void insertAll(EventEntity... events);

    @Delete
    void deleteEvents(EventEntity... eventEntities);


}
