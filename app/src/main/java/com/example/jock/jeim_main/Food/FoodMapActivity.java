package com.example.jock.jeim_main.Food;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jock.jeim_main.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONObject;


public class FoodMapActivity extends AppCompatActivity {
    private MapView mapView;

    private Intent intent;
    private String intentData;
    private JSONArray jsonArray;
    private JSONObject json;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_map);

        mapView = new MapView(this);
        mapView.setDaumMapApiKey("14d59cb85de9198d898d45667ea768d5");
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.foodmap_view);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.475532, 126.650042), true);
        mapView.setZoomLevel(2, true);

        intent = getIntent();
        intentData = intent.getStringExtra("마커");
        getJsonText(intentData);



    }

    private void getJsonText(String jsonvalue){

        try{
            jsonArray = new JSONArray(jsonvalue);
            for(int i=0; i < jsonArray.length();i++){
                json = jsonArray.getJSONObject(i);
                String name =             json.getString("이름");
                double lat    = Double.parseDouble(json.getString("위도"));
                double logn    = Double.parseDouble(json.getString("경도"));
                SetMapMarker(name,lat,logn);
            }

        }catch (Exception e){ e.printStackTrace(); }
    }

    private void SetMapMarker(String name, double lat, double logn){

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat,logn));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양
        mapView.addPOIItem(marker);
    }
}
