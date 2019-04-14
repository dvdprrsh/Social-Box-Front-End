package com.team36.client_frontend;
// David Parrish - 201232252

import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class DrivingEsri extends AppCompatActivity {
    public Snackbar mySnackbar;
    private MapView esriMap;
    private LocationDisplay locationDisplay;
    public String locationProvider;

    Queue<Double> latitudePoints = new LinkedList<>();
    Queue<Double> longitudePoints = new LinkedList<>();
    Queue<Double> timeRecorded = new LinkedList<>();
    Queue<Double>[] queues = new Queue[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_esri);

        // The below is the license ID for the map API
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6686217235,none,C6JC7XLS1MYJ003AD167");
        esriMap = findViewById(R.id.mapView_esriJourney);
        esriMap.setMap(new SetMap(esriMap).map); // Applies the style to the map
        setALocationDisplay();

        Typeface typeface = ResourcesCompat.getFont(this, R.font.quicksand_light);

        Chronometer chronometer = findViewById(R.id.chronometer);
        chronometer.setTypeface(typeface);
        chronometer.start(); // Starts the stopwatch

        mySnackbar = Snackbar.make((findViewById(R.id.constraintLayout)), R.string.driving_snackbar, Snackbar.LENGTH_LONG);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = LocationManager.NETWORK_PROVIDER;

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }catch (SecurityException e){

        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Calendar currenttime = Calendar.getInstance();
            timeRecorded.add((double)currenttime.getTimeInMillis());
            latitudePoints.add(location.getLatitude());
            longitudePoints.add(location.getLongitude());
         //   myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18.0f));

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

    // Sets the location display to display the users location
    private void setALocationDisplay() {
        locationDisplay = esriMap.getLocationDisplay();
        // Navigation pan mode to face the direction of travel and move while the user moves
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
        locationDisplay.startAsync(); // Starts getting tracking location
    }

    // Closes the driving activity and and stops recording
    public void stop_pressed(View view){
        stopRecording();
        finish();
    }

    // This method is used to notify the user that they must press the back button once more to return to the main screen
    @Override
    public boolean onKeyDown(int kCode, KeyEvent kEvent){
        if((!mySnackbar.isShown()) && (kCode == KeyEvent.KEYCODE_BACK)){
            View view = mySnackbar.getView();
            TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mySnackbar.show();
        }else if (mySnackbar.isShown()){
            stopRecording();
            finish();
        }
        return true;
    }

    public String ConvertToJSON() {
        queues[0] = latitudePoints;
        queues[1] = longitudePoints;
        queues[2] = timeRecorded;

        String completeStringToSend = "";

        String[] dataNames = {"Latitude", "Longitude", "TimeLogged"};
        int size = queues[0].size();
        //   try {
        for (int i = 0; i < queues.length;  i++) {
            String toAdd = "";
            for (int x = 0; x < size; x++) {
                if(x == size-1) {
                    toAdd = toAdd + (queues[i].remove());
                } else {
                    toAdd = toAdd + (queues[i].remove() + ",");
                }
            }

            if (i != 0) {
                completeStringToSend = completeStringToSend + "&" + dataNames[i] + "=" + toAdd;
            }
            else {
                completeStringToSend = dataNames[i] + "=" + toAdd;
            }
        }

        return(completeStringToSend);
    }

    private void stopRecording(){
        // This method will be called when the user either presses the 'Stop!' button once or
        // the back button twice
        String postString = ConvertToJSON();
        postString = postString;
        // SendToServer sender = new SendToServer();
        // try {
        //     sender.Send(postString,"");
        // } catch (IOException e) {
        //     e.printStackTrace();
        //}
    }
}
