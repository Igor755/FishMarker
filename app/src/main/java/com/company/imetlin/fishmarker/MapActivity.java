package com.company.imetlin.fishmarker;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.company.imetlin.fishmarker.database.DatabaseLoad;
import com.company.imetlin.fishmarker.database.SQLiteHelper;
import com.company.imetlin.fishmarker.myinterfaces.LinkMarkerLongClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
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

import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_TABLE_NAME;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID_PRIMARY;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LATITUDE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LONGITUDE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_DATE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_DEPTH;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_AMOUNT;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_NOTE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_TITLE;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {



    public GoogleMap googlemap;
    private UiSettings mUiSettings;
    AlertDialog.Builder add_marker;
    private ModelClass modelClass;
    Context context;
    public DatabaseLoad databaseLoad;
    public Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        // button = (ImageButton) findViewById(R.id.imageButton);

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


        //mUiSettings.setMyLocationButtonEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            googlemap.setMyLocationEnabled(true);
            setUpMap(google);


            google.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(final LatLng latLng) {

                    MapClick(latLng.latitude, latLng.longitude);


                }


            });


        } else {
            // Show rationale and request permission.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);


        }


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

                databaseLoad.CreateMarker(res, modelClass.getCoordinates()[0], modelClass.getCoordinates()[1], result);
                System.out.print("I AM SUPERMAN");

            }
            if (resultCode == MapActivity.RESULT_CANCELED) {
                //Write your code if there's no result

                Toast.makeText(context, R.string.unique, Toast.LENGTH_LONG)
                        .show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.settings:
                startActivity(new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


            case R.id.back:
                finish();
                return true;

            case R.id.plus:


                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    LocationManager locationManager = (LocationManager)
                            getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();

                    Location location = locationManager.getLastKnownLocation(locationManager
                            .getBestProvider(criteria, false));
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude))
                            .zoom(15)
                            .build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    googlemap.animateCamera(cameraUpdate);


                     boolean ismarkerExist = DatabaseLoad.getInstance(context).SearchMarker(latitude, longitude);

                    if (!ismarkerExist) {

                        MapClick(latitude, longitude);
                    }else
                    {
                        Toast.makeText(context, R.string.unique, Toast.LENGTH_LONG)
                                .show();
                    }


                    } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            101);


                }

      /*
                /////////////////////////////////////DEPRECATED method.getMylocation()


                Location location = this.googlemap.getMyLocation();

                if (location != null) {

                    LatLng target = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraPosition position = this.googlemap.getCameraPosition();

                    CameraPosition.Builder builder = new CameraPosition.Builder();
                    builder.zoom(15);
                    builder.target(target);

                    this.googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));

                }*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void MapClick(final double lat, final double lon) {

        StringBuilder stringBuilder = new StringBuilder();

        ((MapActivity) context).modelClass = new ModelClass(lat, lon);
        add_marker = new AlertDialog.Builder(context);
        add_marker.setTitle(R.string.addmarker);  // заголовок

        stringBuilder.append(context.getResources().getString(R.string.coordinate_add));
        stringBuilder.append("\n");
        stringBuilder.append(context.getResources().getString(R.string.lat_c) + " " + lat);
        stringBuilder.append("\n");
        stringBuilder.append(context.getResources().getString(R.string.lon_c) + " " + lon);
        add_marker.setMessage(stringBuilder); // сообщение
        add_marker.setIcon(R.drawable.fish2);


        add_marker.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {


                String a = String.valueOf(lat) + "/" + String.valueOf(lon);

                Intent intent = new Intent(MapActivity.this, CardMarkerActivity.class);
                intent.putExtra("coordinate", a);
                startActivityForResult(intent, 1);


            }
        });
        add_marker.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, R.string.cancel, Toast.LENGTH_LONG)
                        .show();
            }
        });
        add_marker.setCancelable(true);
        add_marker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, R.string.ups,
                        Toast.LENGTH_LONG).show();
            }
        });

        add_marker.show();


    }


}