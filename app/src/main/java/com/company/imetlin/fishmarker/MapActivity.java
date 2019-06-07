package com.company.imetlin.fishmarker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.company.imetlin.fishmarker.gps.GPSTracker;
import com.company.imetlin.fishmarker.database.DatabaseLoad;
import com.company.imetlin.fishmarker.pojo.MarkerInformation;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import com.google.android.gms.maps.*;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;



public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener/*, NavigationBar.BottomSheetListener*/{


    public GoogleMap googlemap;
    private UiSettings mUiSettings;
    AlertDialog.Builder add_marker;
    private ModelClass modelClass;
    Context context;
    public Menu menu;

    public double latitude, longitude;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE_TWO = 2;

    LinearLayout linearLayout;


    private Button edit, detail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = MapActivity.this;

        linearLayout = (LinearLayout) findViewById(R.id.bottom_sheet);
        edit = (Button) findViewById(R.id.button_edit);
        detail = (Button) findViewById(R.id.button_detail);
        linearLayout.setVisibility(View.GONE);

        DatabaseLoad.getInstance().setContext(context);



    }

    @Override
    public void onMapReady(GoogleMap google) {


        this.googlemap = google;
        mUiSettings = google.getUiSettings();
        google.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);




        onCheckPermission(google);

        //mUiSettings.setMyLocationButtonEnabled(true);
        setUpMap(google);

//////////////////////LONG CLICK MAP
        google.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {

                MapClick(latLng.latitude, latLng.longitude);



            }


        });



//////////////////////CLICK MARKER
        google.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                marker.showInfoWindow();

                mUiSettings.setZoomControlsEnabled(false);

                final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show);
                linearLayout.startAnimation(show);

                linearLayout.setVisibility(View.VISIBLE);


                detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseLoad.getInstance().DetailMarker(marker);

                        Toast.makeText(MapActivity.this, marker.getId(), Toast.LENGTH_SHORT).show();
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isMyMarker = false;

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        for (MarkerInformation item:DatabaseLoad.getInstance().alldatamarkers) {


                            if(item.getLongitude() == marker.getPosition().longitude &&
                                    item.getLatitude() == marker.getPosition().latitude &&
                                    item.getTitle().equals(marker.getTitle()) &&
                                    userId.equals(item.getUid()))
                            {
                                isMyMarker = true;
                                break;
                            }

                        }
                        if(isMyMarker){

                            DatabaseLoad.getInstance().UpdateMarkerOfMapActivity(marker);
                        }
                        else{

                            Toast.makeText(MapActivity.this, "Don't edit/delete foreign markers ", Toast.LENGTH_SHORT).show();

                        }


                    }
                });



                return true;
            }
        });



/////////////////////////Click on MAP

        google.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (linearLayout.getVisibility() == View.VISIBLE) {

                    final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
                    linearLayout.startAnimation(hide);
                    linearLayout.setVisibility(View.GONE);
                    mUiSettings.setZoomControlsEnabled(true);
                }else {
                    linearLayout.setVisibility(View.GONE);
                    mUiSettings.setZoomControlsEnabled(true);
                }
            }
        });



    }

    public void onCheckPermission(GoogleMap googl) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            googl.setMyLocationEnabled(true);
            // System.out.print(2);


        } else {
            // Show rationale and request permission.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        }


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:

                // Check Location permission is granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapActivity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();

                    onCheckPermission(googlemap);


                } else {
                    Toast.makeText(MapActivity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case LOCATION_PERMISSION_REQUEST_CODE_TWO:
                // Check Location permission is granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapActivity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();

                    onMyLocation();
                    googlemap.setMyLocationEnabled(true);


                } else {
                    Toast.makeText(MapActivity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void setUpMap(GoogleMap google) {


        this.googlemap = google;
      /*  double[] cats = getIntent().getDoubleArrayExtra("coordinates");
        Integer myZoom = getIntent().getExtras().getInt("zoom");*/

        Bundle arguments = getIntent().getExtras();


            String latitude_water = arguments.get("latitude").toString();
            String longitude_water = arguments.get("longitude").toString();
            String zoom_water = arguments.get("zoom").toString();

            Double lat = Double.valueOf(latitude_water);
            Double lon = Double.valueOf(longitude_water);
            Float zom = Float.valueOf(zoom_water);




        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(zom)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        google.animateCamera(cameraUpdate);


        DatabaseLoad.getInstance().LoaderData(google);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode == MapActivity.RESULT_OK) {


                String title_marker = data.getStringExtra("title");
                String id_marker = data.getStringExtra("id");


                Resources res = context.getResources();
                final Drawable my_icons_fish = res.getDrawable(R.drawable.fish_my_30);
                final Bitmap bitmap_my = ((BitmapDrawable)my_icons_fish).getBitmap();


               DatabaseLoad.getInstance().CreateMarker(modelClass.getCoordinates()[0], modelClass.getCoordinates()[1], title_marker, bitmap_my);
               System.out.print("I AM SUPERMAN");

            }
            if (resultCode == MapActivity.RESULT_CANCELED) {
                //Write your code if there's no result

                Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT)
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


          /*  case R.id.settings:
                startActivity(new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));*/


            case R.id.back:
                finish();
                return true;

            case R.id.plus:

                onMyLocation();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onMyLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            GPSTracker gps = new GPSTracker(MapActivity.this);
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();


            if ((latitude == 0.0) && (longitude == 0.0)) {
                gps.showSettingsAlert();

            } else {

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(15)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                googlemap.animateCamera(cameraUpdate);


               boolean ismarkerExist = DatabaseLoad.getInstance().SearchMarker(latitude, longitude);

                if (!ismarkerExist) {

                    MapClick(latitude, longitude);
                } else {
                    Toast.makeText(context, R.string.unique, Toast.LENGTH_SHORT)
                            .show();
                }

            }

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    2);


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
                Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        add_marker.setCancelable(true);
        add_marker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, R.string.ups,
                        Toast.LENGTH_SHORT).show();
            }
        });

        add_marker.show();


    }


}