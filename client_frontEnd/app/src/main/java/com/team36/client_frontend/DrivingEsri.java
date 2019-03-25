package com.team36.client_frontend;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

public class DrivingEsri extends AppCompatActivity {
    final Double MIN_SCALE = 7200.0;
    final Double MAX_SCALE = 6400.0;

    public Snackbar mySnackbar;

    private MapView esriMap;
    private LocationDisplay locationDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_esri);

        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6686217235,none,C6JC7XLS1MYJ003AD167");
        esriMap = findViewById(R.id.mapView_esri);
        setMap();
        setALocationDisplay();

        Chronometer chronometer = findViewById(R.id.chronometer);
        chronometer.start();

        mySnackbar = Snackbar.make((findViewById(R.id.constraintLayout)), R.string.driving_snackbar, Snackbar.LENGTH_LONG);
    }

    private void setMap() {
        if (esriMap != null) {
            Basemap.Type navigation_map = Basemap.Type.NAVIGATION_VECTOR;
            ArcGISMap map = new ArcGISMap(navigation_map, 0.0, 0.0, 1);
            map.setMinScale(MIN_SCALE);
            map.setMaxScale(MAX_SCALE);
            esriMap.setMap(map);
        }
    }

    private void setALocationDisplay() {
        locationDisplay = esriMap.getLocationDisplay();
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
        locationDisplay.startAsync();
    }

    public void stop_pressed(View view){
        stopRecording();
        finish();
    }

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

    private void stopRecording(){
        // This method will be called when the user either presses the 'Stop!' button once or
        // the back button twice
    }
}
