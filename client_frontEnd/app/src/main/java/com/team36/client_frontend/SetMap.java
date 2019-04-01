package com.team36.client_frontend;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class SetMap {
    final Double MIN_SCALE = 7200.0;
    final Double MAX_SCALE = 6400.0;

    public ArcGISMap map;

    SetMap(MapView esriMap){
        // Sets the look of the map
        if (esriMap != null) {
            // Sets the look of the map to a navigation style to make roads stand out more
            Basemap.Type navigation_map = Basemap.Type.NAVIGATION_VECTOR;
            map = new ArcGISMap(navigation_map, 0.0, 0.0, 1);
            // Sets the minimum and maximum zoom levels
            map.setMinScale(MIN_SCALE);
            map.setMaxScale(MAX_SCALE);
        }
    }
}
