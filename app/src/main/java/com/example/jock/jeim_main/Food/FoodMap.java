package com.example.jock.jeim_main.Food;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.ViewGroup;

import com.example.jock.jeim_main.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;



public class FoodMap extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_map);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("14d59cb85de9198d898d45667ea768d5");
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.foodmap_view);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.475532, 126.650042), true);


    }
}
