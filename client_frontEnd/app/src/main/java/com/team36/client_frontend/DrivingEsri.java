package com.team36.client_frontend;
// David Parrish - 201232252

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class DrivingEsri extends AppCompatActivity implements ServerResponded {
    public Snackbar snackbar;
    private MapView esriMap;
    private LocationDisplay locationDisplay;
    public String locationProvider;
    private String trip_id;
    LoggedIn_User loggedIn_user = new LoggedIn_User();

    //**** Cameron MacKay ****//
    Queue<Double> latitudePoints = new LinkedList<>();
    Queue<Double> longitudePoints = new LinkedList<>();
    Queue<Double> timeRecorded = new LinkedList<>();
    Queue<Double>[] queues = new Queue[3];
    //********//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_esri);

        // The below is the license ID for the map API
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6686217235,none,C6JC7XLS1MYJ003AD167");
        esriMap = findViewById(R.id.mapView_esriJourney);
        esriMap.setMap(new SetMap(esriMap).map); // Applies the style to the map
        setALocationDisplay();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton_resetView);
        floatingActionButton.setOnClickListener(this::reset_map);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.quicksand_light);

        Chronometer chronometer = findViewById(R.id.chronometer);
        chronometer.setTypeface(typeface);
        chronometer.start(); // Starts the stopwatch to show how long it has been recording

        snackbar = Snackbar.make((findViewById(R.id.constraintLayout)), R.string.driving_snackbar, Snackbar.LENGTH_LONG); // Snackbar for when the back button is pressed

        startRecording();
    }

    //**** Cameron MacKay ****//
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
    //********//

    /*
     * Lines 108 to 128 pause the map to save battery when the user leaves the app;
     * resume the map if the user returns to the app after leaving it;
     * and destroys the map if the activity is destroyed by the OS or otherwise.
     * These are all done only if the map is not null (is displaying the users location)
     */
    @Override
    public void onPause() {
        if (esriMap!=null){
            esriMap.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (esriMap!=null){
            esriMap.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (esriMap!=null){
            esriMap.dispose();
        }
        finish();
        super.onDestroy();
    }

    private void startTrip() {
        //**** Cameron MacKay ****//
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = LocationManager.NETWORK_PROVIDER;

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }catch (SecurityException e){

        }
        //********//
    }

    // Sets the location display to display the users location
    private void setALocationDisplay() {
        locationDisplay = esriMap.getLocationDisplay();
        // Navigation pan mode to face the direction of travel and move while the user moves
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
        locationDisplay.startAsync(); // Starts getting tracking location
    }

    public void reset_map(View view){
        setALocationDisplay();
    }

    // Closes the driving activity and and stops recording
    public void stop_pressed(View view){
        String toSend = ConvertToJSON();
        toSend = (toSend + "&trip_id=" + trip_id + "&api_key=" + loggedIn_user.api);
        //Send to the server
        new ServerSender(DrivingEsri.this).execute(toSend, "http://social-box.xyz/api/update_trip", "");
        stopRecording();
    }

    // This method is used to notify the user that they must press the back button twice to return to the main screen
    @Override
    public boolean onKeyDown(int kCode, KeyEvent kEvent){
        if((!snackbar.isShown()) && (kCode == KeyEvent.KEYCODE_BACK)){
            View view = snackbar.getView();
            TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        }else if (snackbar.isShown()){
            stopRecording();
            finish();
        }
        return true;
    }

    //**** Cameron MacKay ****//
    public String ConvertToJSON() {
        queues[0] = latitudePoints;
        queues[1] = longitudePoints;
        queues[2] = timeRecorded;

        String completeStringToSend = "";

        String[] dataNames = {"lat", "long", "timestamps"};
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
    //********//

    private void stopRecording(){
        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra("Id", trip_id);
        startActivity(intent);

        finish();
    }



    private void startRecording() {
        String toSend = ("api_key="+loggedIn_user.api);
        //Send to the server
        new ServerSender(DrivingEsri.this).execute(toSend, "http://social-box.xyz/api/begin_trip", "");
    }


    @Override
    public void onTaskComplete(String result) {

        try {
            JSONObject json = new JSONObject(result);
            if(json.has("trip_id")) {
                trip_id = json.getString("trip_id");
                startTrip();
            } else {
                //stopRecording();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
