package com.team36.client_frontend;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class DrivingActivity extends AppCompatActivity implements OnMapReadyCallback {
    public Snackbar mySnackbar;
    public Location lastKnown;
    public String locationProvider;
    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        SupportMapFragment mapView_driving = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView_driving);
        mapView_driving.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = LocationManager.NETWORK_PROVIDER;

        mySnackbar = Snackbar.make((findViewById(R.id.contraintLayout)), R.string.driving_snackbar, Snackbar.LENGTH_LONG);

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        }catch (SecurityException e){

        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18.0f));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        setMap();
        try{
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude()), 18.0f));
        }catch (NullPointerException e){

        }
    }

    private void setMap(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
                myMap.setMyLocationEnabled(true);
            }
        }else{
            myMap.setMyLocationEnabled(true);
        }
    }

    public void stop_pressed(View view){
        stopRecording();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (!mySnackbar.isShown()){
            if (keyCode == KeyEvent.KEYCODE_BACK){
                View view = mySnackbar.getView();
                TextView myTextView = view.findViewById(android.support.design.R.id.snackbar_text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    myTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }else{
                    myTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                mySnackbar.show();
            }
        }else if (mySnackbar.isShown()){
            stopRecording();
            finish();
        }
        return true;
    }

    private void stopRecording(){
        // This method will be called when the user either presses the back button twice or the stop button is called
    }
}
