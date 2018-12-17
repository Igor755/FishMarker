package com.company.imetlin.fishmarker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.company.imetlin.fishmarker.database.DatabaseLoad;
import com.company.imetlin.fishmarker.database.SQLiteHelper;
import com.company.imetlin.fishmarker.myinterfaces.LinkMarkerLongClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {


    //SupportMapFragment mapFragment;
    //GoogleMap map;
    final String TAG = "myLogs";
    public GoogleMap googlemap;
    private UiSettings mUiSettings;
    AlertDialog.Builder add_marker;
    private ModelClass modelClass;
    Context context;
    public DatabaseLoad databaseLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = MapActivity.this;


    }

    @Override
    public void onMapReady(GoogleMap google) {


        this.googlemap = google;
        mUiSettings = google.getUiSettings();
        google.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);



        mUiSettings.setMyLocationButtonEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            google.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        setUpMap(google);



        google.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {


                StringBuilder stringBuilder = new StringBuilder();

                ((MapActivity) context).modelClass = new ModelClass(latLng.latitude, latLng.longitude);
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


                        Intent intent = new Intent(MapActivity.this, CardMarkerActivity.class);
                        intent.putExtra("coord", latLng.toString());
                        startActivityForResult(intent, 1);


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




        this.googlemap = google;
        double[] cats = getIntent().getDoubleArrayExtra("coordinates");
        Integer myZoom = getIntent().getExtras().getInt("zoom");


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(cats[0], cats[1]))
                .zoom(myZoom)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        google.animateCamera(cameraUpdate);


        databaseLoad = DatabaseLoad.getInstance(context);
        databaseLoad.LoaderData(google);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == 1) {
            if (resultCode == MapActivity.RESULT_OK) {
                String result = data.getStringExtra("result");

                String result2 = data.getStringExtra("id");
                int res = Integer.parseInt(result2);

               databaseLoad.CreateMarker(res,modelClass.getCoordinates()[0],modelClass.getCoordinates()[1],result);
               System.out.print("I AM SUPERMAN");

            }
            if (resultCode == MapActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }


        }
    }


}