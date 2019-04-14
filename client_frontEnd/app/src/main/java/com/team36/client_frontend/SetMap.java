package com.team36.client_frontend;
// David Parrish - 201232252

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class SetMap {
    ArcGISMap map;

    // Sets the look of the map
    SetMap(MapView esriMap){
        // Sets the look of the map
        if (esriMap != null) {
            // Sets the look of the map to a navigation style to make roads stand out more
            map = new ArcGISMap(Basemap.createNavigationVector());
        }
    }
}
