package com.company.imetlin.fishmarker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {


    //SupportMapFragment mapFragment;
    //GoogleMap map;
    final String TAG = "myLogs";
    GoogleMap googleMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mUiSettings = googleMap.getUiSettings();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mUiSettings.setZoomControlsEnabled(true);


        mUiSettings.setMyLocationButtonEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        setUpMap(googleMap);




    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void setUpMap(GoogleMap google) {

        double[] cats  = getIntent().getDoubleArrayExtra("coordinates");


     /*   google.moveCamera(CameraUpdateFactory.zoomTo(5));
        LatLngBounds ADELAIDE = new LatLngBounds(
                new LatLng(40.95160008, 28.82555172), new LatLng(45.04704915, 38.11998531),10);
        google.setLatLngBoundsForCameraTarget(ADELAIDE);
*/



        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(cats[0], cats[1]))
                .zoom(5)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        google.animateCamera(cameraUpdate);












        //создаем координаты для позиции камеры с центром в городе Киев
        //LatLng positions = new LatLng(50.452842, 30.524418);
        //перемещаем камеру и оттдаляем ее что мы можно было увидеть город
        //google.moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 10));
        //Добавляем маркер с местоположением на Крещатике
        //mMap.addMarker(new MarkerOptions().position(new LatLng(50.450137, 30.524180)).title("Крещатик")); }
    }

}