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

import static android.graphics.Color.rgb;


public class JourneyFragment extends Fragment {
    private final String WELCOME_MESSAGE = "%s's Journey";
    private final SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 4);
    private final SimpleMarkerSymbol simpleMarkerSymbolStart = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, rgb(81, 184, 74), 10);
    private final SimpleMarkerSymbol simpleMarkerSymbolEnd = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);

    private View returnView;
    private Bundle arguments;

    private MapView esriMap;

    private double[] xCoords = {-2.936182, -2.930346};
    private double[] yCoords = {53.394124, 53.390423};
    private final int START_COORDINATE = 0;
    private int END_COORDINATE;

    private Point pointStart;
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

        END_COORDINATE = (xCoords.length)-1;

        setStart();
        return setView.returnView;
    }

    private void setStart(){
        PointCollection points = new PointCollection(SpatialReferences.getWgs84());
        pointStart = new Point(xCoords[0], yCoords[0], SpatialReferences.getWgs84());
        for (int i=0; i<xCoords.length; i++){
            points.add(xCoords[i], yCoords[i]);
            if (i == (xCoords.length)-1) pointEnd = new Point(xCoords[i], yCoords[i], SpatialReferences.getWgs84());
        }

        Polyline polyline = new Polyline(points, SpatialReferences.getWgs84());

        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        esriMap.getGraphicsOverlays().add(graphicsOverlay);
        graphicsOverlay.getGraphics().add(new Graphic(polyline, simpleLineSymbol));
        graphicsOverlay.getGraphics().add(new Graphic(pointStart, simpleMarkerSymbolStart));
        graphicsOverlay.getGraphics().add(new Graphic(pointEnd, simpleMarkerSymbolEnd));

        Envelope envelope = new Envelope(xCoords[START_COORDINATE], yCoords[START_COORDINATE], xCoords[END_COORDINATE], yCoords[END_COORDINATE], SpatialReferences.getWgs84());
        esriMap.setViewpointGeometryAsync(envelope);
    }
}
