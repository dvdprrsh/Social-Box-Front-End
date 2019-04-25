package com.team36.client_frontend;
// David Parrish - 201232252

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import org.json.JSONException;
import org.json.JSONObject;

import static android.graphics.Color.rgb;


public class JourneyFragment extends Fragment implements ServerResponded{
    private final String WELCOME_MESSAGE = "%s's Journey";
    private final SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 4);
    private final SimpleMarkerSymbol simpleMarkerSymbolStart = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, rgb(81, 184, 74), 10);
    private final SimpleMarkerSymbol simpleMarkerSymbolEnd = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);

    private View returnView;
    private Bundle arguments;

    private double[] xCoords = {-2.936182, -2.930346};
    private double[] yCoords = {53.394124, 53.390423};
    private MapView esriMap;
    private double[] coords = {-2.936182, 53.394124, -2.930346, 53.390423};

    private final int START_COORDINATE = 0;
    private int END_COORDINATE;

    private Point pointEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_journey, container, false);

        arguments = getArguments();
        ReturnView setView = new ReturnView(returnView, WELCOME_MESSAGE, arguments);

        // The below is the license ID for the map API
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6686217235,none,C6JC7XLS1MYJ003AD167");
        esriMap = returnView.findViewById(R.id.mapView_esriJourney);
        ArcGISMap arcGISMap = new ArcGISMap(Basemap.createNavigationVector());
        esriMap.setMap(arcGISMap);

        END_COORDINATE = (coords.length)-1;

        setRoute();
        return setView.returnView;
    }

    /*
     * Lines 74 to 94 pause the map to save battery when the user leaves the app;
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
        super.onDestroy();
    }

    // Displays the route for the given journey
    private void setRoute(){
        PointCollection points = new PointCollection(SpatialReferences.getWgs84());
        Point pointStart = new Point(coords[0], coords[1], SpatialReferences.getWgs84());

        for (int i=0; i<coords.length;){
            points.add(coords[i], coords[i+1]);
            if (i == (coords.length)-2){
                pointEnd = new Point(coords[i], coords[i+1], SpatialReferences.getWgs84());
            }
            i=i+2;
        }

        Polyline polyline = new Polyline(points, SpatialReferences.getWgs84()); // Creates the journey route line

        // Adds the route with start and end points to the graphics overlay and displays it
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        esriMap.getGraphicsOverlays().add(graphicsOverlay);
        graphicsOverlay.getGraphics().add(new Graphic(polyline, simpleLineSymbol));
        graphicsOverlay.getGraphics().add(new Graphic(pointStart, simpleMarkerSymbolStart));
        graphicsOverlay.getGraphics().add(new Graphic(pointEnd, simpleMarkerSymbolEnd));

        Envelope envelope = new Envelope(coords[START_COORDINATE], coords[START_COORDINATE+1], coords[END_COORDINATE-1], coords[END_COORDINATE], SpatialReferences.getWgs84());
        esriMap.setViewpointGeometryAsync(envelope); // Sets the view of the map to scale to the start and endpoint
    }


    //This does all the server shit
    private void GetData(String apiKey, String tripId) {
        String toSend = "api_key=" + apiKey + "trip_id=" + tripId;
        new ServerSender(getActivity()).execute(toSend, "http://social-box.xyz/api/login", "");
    }

    private void handleData(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
    }

    @Override
    public void onTaskComplete(String result) {

        try {
            handleData(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
