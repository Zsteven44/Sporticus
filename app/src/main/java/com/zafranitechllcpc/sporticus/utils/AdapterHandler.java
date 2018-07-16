package com.zafranitechllcpc.sporticus.utils;

import android.support.annotation.NonNull;

import com.zafranitechllcpc.sporticus.activities.BaseActivity;
import com.zafranitechllcpc.sporticus.locations.LocationAdapter;
import com.zafranitechllcpc.sporticus.permissions.PermissionAdapter;

public class AdapterHandler {
    private BaseActivity activity;
    private LocationAdapter locationAdapter;
    private PermissionAdapter permissionAdapter;

    public AdapterHandler(@NonNull final BaseActivity activity) {
        this.activity = activity;
    }

    public LocationAdapter getLocationAdapter() {
        if (locationAdapter == null) locationAdapter = new LocationAdapter(activity);
        return locationAdapter;
    }

    public PermissionAdapter getPermissionAdapter() {
        if (locationAdapter == null) permissionAdapter = new PermissionAdapter(activity);
        return permissionAdapter;
    }
}
