package com.zafranitechllcpc.sporticus.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.zafranitechllcpc.sporticus.R;
import com.zafranitechllcpc.sporticus.database.AppDatabase;
import com.zafranitechllcpc.sporticus.database.entity.EventEntity;
import com.zafranitechllcpc.sporticus.fragments.AddEventFragment;
import com.zafranitechllcpc.sporticus.fragments.BaseFragment;
import com.zafranitechllcpc.sporticus.fragments.EventsFragment;
import com.zafranitechllcpc.sporticus.fragments.MapFragment;
import com.zafranitechllcpc.sporticus.fragments.NewsFragment;
import com.zafranitechllcpc.sporticus.fragments.utils.FragmentAssistant;
import com.zafranitechllcpc.sporticus.viewmodels.EventViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private final String TAG = getClass().getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private FragmentAssistant fragmentAssistant;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleApiClient googleApiClient;
    private BaseFragment activeFragment;
    private AppDatabase appDatabase;
    private EventViewModel eventViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildGoogleApiClient();
    }

    //region activity lifecycle methods
    @Override
    protected void onStart() {
        super.onStart();
        this.appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        buildDummyDatabaseData();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment activeFragment = getFragmentAssistant().getActiveActionFragment();
                switch (id){
                    case (R.id.action_map):
                        if (activeFragment instanceof MapFragment) return true;
                        getFragmentAssistant()
                                .changeFragment(R.id.action_container, MapFragment.getInstance(), false);
                        return true;
                    case (R.id.action_news):
                        if (activeFragment instanceof NewsFragment) return true;
                        getFragmentAssistant()
                                .changeFragment(R.id.action_container, NewsFragment.getInstance(), false);
                        return true;
                    case (R.id.action_events):
                        if (activeFragment instanceof EventsFragment) return true;
                        getFragmentAssistant()
                                .changeFragment(R.id.action_container, EventsFragment.getInstance(), false);
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_map);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient == null) {
            buildGoogleApiClient();
        }
    }

    //endregion

    // region GoogleApiClient
    public GoogleApiClient buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return googleApiClient;
    }
    // endregion

    // region Initialize DB data
    public void buildDummyDatabaseData() {
        Log.e(TAG,"Building dummy data.");
        int dummyDataSize = 10;
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        EventEntity[] eventEntities = new EventEntity[dummyDataSize];
        for (int i = 0; i < dummyDataSize; i++) {
            eventEntities[i] = (new EventEntity("Event"+i, (float) Math.random()*180, (float) Math.random()*180));
            Log.e(TAG, "Creating dummy EventEntity for database.");
        }
        eventViewModel.insertEvents(eventEntities);
    }
    // endregion


    public FragmentAssistant getFragmentAssistant() {
        if (fragmentAssistant == null) fragmentAssistant = new FragmentAssistant(this);
        return fragmentAssistant;
    }



    // region Location
    // Google Location Services API (GoogleApiClient.ConnectionCallbacks,
    // GoogleApiClient.OnConnectionFailedListener)
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // endregion


}
