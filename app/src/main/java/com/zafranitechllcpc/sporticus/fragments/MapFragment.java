package com.zafranitechllcpc.sporticus.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zafranitechllcpc.sporticus.activities.BaseActivity;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, LocationListener {
    /* Notes
    FusedLocationProviderClient ->
    https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest.html#PRIORITY_HIGH_ACCURACY
     */

    private final String TAG = getClass().getSimpleName();
    private final long UPDATE_INTERVAL = 10000;
    private final long FASTEST_INTERVAL = 2000;
    private final float MAXIMUM_ZOOM = 18;
    private final float MINIMUM_ZOOM = 8;
    private final float DEFAULT_ZOOM = 15;
    private GoogleMap googleMap;
    private BaseActivity activity;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            onLocationChanged(locationResult.getLastLocation());
        }
    };
    private boolean panCamera = false;


    public static MapFragment getInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (BaseActivity) getActivity();
        Log.e(TAG, TAG+ " onViewCreated");
        Toast.makeText(activity, "I am " + TAG, Toast.LENGTH_SHORT).show();
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            this.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Toast.makeText(activity, "onMapReady", Toast.LENGTH_SHORT).show();
        this.googleMap = googleMap;
        googleMap.setMaxZoomPreference(MAXIMUM_ZOOM);
        googleMap.setMinZoomPreference(MINIMUM_ZOOM);
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        // checks if necessary permission is granted
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // if not granted, runs the necessary permission dialogs
            ((BaseActivity) activity).getAdapterHandler().getPermissionAdapter().checkLocationPermission();
        } else {
            requestLocationUpdates();
            googleMap.setMyLocationEnabled(true);
        }
    }

    public void requestLocationUpdates() {
        /*
            If granted, utilizes FusedLocationProviderClient to retrieve last known location

            1.) First step is to create LocationRequest to start receiving updates.

            PRIORITY_HIGH_ACCURACY will give the finest location updates, at the UPDATE_INTERVAL.
            The FastestInterval is the rate at which this locationRequest can use location updates
            from other applications.

            */
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

            /*
            2.) Create a LocationSettingsRequest object based on our LocationRequest.

            LocationSettingsRequest utilizes a builder pattern.  Add all of our LocationRequests
            that the app will be using. Then build the LocationSettingsRequest.

             */
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

            /*
            3.) Check whether location settings are satisfied.

            SettingsClient ->
            https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            This is the main entry point for interacting with the location settings-enabler APIs.
            When making a request to location services, the device's system settings may be in a
            state that prevents an app from obtaining the location data that it needs.  Example,
            GPS or WiFi scanning may be switched off... This intent makes it easy to:
                - determine if relevant system settings are enabled on the device to carry out
                    desired location request.
                - Optionally, invoke a dialog that allows the user to enable the necessary location
                    settings with a single tap.
            The checkLocationSettings call will check that location settings are satisfied, returning
            status code in a LocationSettingsResponse object.
             */

        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
        settingsClient.checkLocationSettings(locationSettingsRequest);

            /*
            Since Google API SDK v11, use getFusedLocationProviderClient.
             */
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // if not granted, runs the necessary permission dialogs
            ((BaseActivity) activity).getAdapterHandler().getPermissionAdapter().checkLocationPermission();
        } else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (!panCamera){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
            panCamera=true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationProviderClient != null) fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fusedLocationProviderClient != null) requestLocationUpdates();
    }
}
