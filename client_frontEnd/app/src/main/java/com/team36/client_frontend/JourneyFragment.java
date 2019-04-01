package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;


public class JourneyFragment extends Fragment {
    private final String WELCOME_MESSAGE = "%s's Journey";
    private final Viewpoint startPoint = new Viewpoint(53.393031,-2.934257,72000);

    private View returnView;
    private Bundle arguments;
    private MapView esriMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_journey, container, false);

        arguments = getArguments();
        ReturnView setView = new ReturnView(returnView, WELCOME_MESSAGE, arguments);

        // The below is the license ID for the map API
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6686217235,none,C6JC7XLS1MYJ003AD167");

        esriMap = returnView.findViewById(R.id.mapView_esriJourney);
        esriMap.setMap(new SetMap(esriMap).map);

        setStart();

        return setView.returnView;
    }

    private void setStart(){
        esriMap.getMap().setInitialViewpoint(startPoint);

        // TODO: Fix!
        /*PointCollection points = new PointCollection(SpatialReferences.getWgs84());
        points.add(53.393031,-2.934257);
        points.add(53.390278, -2.930114);


        PolylineBuilder polylineBuilder = new PolylineBuilder(points);

        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFFFF0000, 1f);
        Graphic tempGraphic = new Graphic(polylineBuilder.toGeometry(), simpleLineSymbol);
        tempGraphic.setGeometry(polylineBuilder.toGeometry());

        esriMap.getGraphicsOverlays().add(new GraphicsOverlay());
        esriMap.getGraphicsOverlays().get(0).getGraphics().add(tempGraphic);*/
    }

}
