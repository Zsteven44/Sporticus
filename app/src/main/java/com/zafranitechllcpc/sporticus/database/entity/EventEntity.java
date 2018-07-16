package com.zafranitechllcpc.sporticus.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "events")
public class EventEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "event_name")
    private String eventName;
    @ColumnInfo(name = "event_lat")
    private float eventLat;
    @ColumnInfo(name = "event_lng")
    private float eventLng;

    public EventEntity(@NonNull final String eventName,
                       final float eventLat,
                       final float eventLng) {
        this.eventName = eventName;
        this.eventLat = eventLat;
        this.eventLng = eventLng;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public float getEventLat() {
        return eventLat;
    }

    public void setEventLat(float eventLat) {
        this.eventLat = eventLat;
    }

    public float getEventLng() {
        return eventLng;
    }

    public void setEventLng(float eventLng) {
        this.eventLng = eventLng;
    }


    @Override
    public String toString() {
        return "EventEntity{" +
                "uid=" + uid +
                ", eventName='" + eventName + '\'' +
                ", eventLat=" + eventLat +
                ", eventLng=" + eventLng +
                '}';
    }
}
