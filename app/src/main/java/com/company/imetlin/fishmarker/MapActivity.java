package com.company.imetlin.fishmarker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
    GoogleMap gogleMap;
    private UiSettings mUiSettings;
    AlertDialog.Builder add_marker;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap gogleMap) {


        mUiSettings = gogleMap.getUiSettings();
        gogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mUiSettings.setZoomControlsEnabled(true);


        mUiSettings.setMyLocationButtonEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gogleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        setUpMap(gogleMap);


        gogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


                StringBuilder stringBuilder = new StringBuilder();

                context = MapActivity.this;
                add_marker = new AlertDialog.Builder(context);
                add_marker.setTitle(R.string.addmarker);  // заголовок

                stringBuilder.append("Coordinate will be add on base");
                stringBuilder.append("\n");
                stringBuilder.append(latLng.toString());
                add_marker.setMessage(stringBuilder); // сообщение
                add_marker.setIcon(R.drawable.fish2);


                add_marker.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "Вы сделали правильный выбор",
                                Toast.LENGTH_LONG).show();
                    }
                });
                add_marker.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "Возможно вы правы", Toast.LENGTH_LONG)
                                .show();
                    }
                });
                add_marker.setCancelable(true);
                add_marker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "Вы ничего не выбрали",
                                Toast.LENGTH_LONG).show();
                    }
                });

                add_marker.show();
            }



        });


    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void setUpMap(GoogleMap google) {

        double[] cats = getIntent().getDoubleArrayExtra("coordinates");
        Integer myZoom = getIntent().getExtras().getInt("zoom");


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(cats[0], cats[1]))
                .zoom(myZoom)
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