package com.company.imetlin.fishmarker.userplaces;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.firebaseAuth.RegistrationActivity;
import com.company.imetlin.fishmarker.pojo.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class PlacesUserMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap googlemap;
    private UiSettings mUiSettings;
    AlertDialog.Builder add_place_marker_dialog;
    Context context;
    public Menu menu;
    private Places modelClass;
    private TextView latitude_txt;
    private TextView longitude_txt;
    private TextView zoom_txt;
    private EditText name_water_object;
    private String id_place_key;

    private TextView title_alert;

    private PlacesUserActivity placesUserActivity;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE_TWO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_user_map_activity);

        this.id_place_key = id_place_key;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = PlacesUserMapActivity.this;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googlemap = googleMap;
        mUiSettings = googleMap.getUiSettings();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mUiSettings.setZoomControlsEnabled(true);

        onCheckPermission(googlemap);


        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {

                MapClick(latLng.latitude, latLng.longitude);



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
                    Toast.makeText(PlacesUserMapActivity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();

                    onCheckPermission(googlemap);


                } else {
                    Toast.makeText(PlacesUserMapActivity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case LOCATION_PERMISSION_REQUEST_CODE_TWO:
                // Check Location permission is granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PlacesUserMapActivity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();


                    googlemap.setMyLocationEnabled(true);


                } else {
                    Toast.makeText(PlacesUserMapActivity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_water, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.back:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressLint("SetTextI18n")
    public void MapClick(final double lat, final double lon) {

        Intent intent = getIntent();
        this.placesUserActivity = new PlacesUserActivity();


        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.alert, null);
        add_place_marker_dialog = new AlertDialog.Builder(context);



        //Настраиваем prompt.xml для нашего AlertDialog:
        add_place_marker_dialog.setView(promptsView);


        title_alert = (TextView) promptsView.findViewById(R.id.title_alert);
        name_water_object = (EditText) promptsView.findViewById(R.id.input_text);
        latitude_txt = (TextView) promptsView.findViewById(R.id.latitude_alert);
        longitude_txt = (TextView) promptsView.findViewById(R.id.longitude_alert);
        zoom_txt = (TextView) promptsView.findViewById(R.id.zoom_alert);

        final float zoom = googlemap.getCameraPosition().zoom;

        latitude_txt.setText(context.getResources().getString(R.string.lat_c) + " " + lat);
        longitude_txt.setText(context.getResources().getString(R.string.lon_c) + " " + lon);
        zoom_txt.setText(context.getResources().getString(R.string.zoom) + " " + zoom);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "alert_font_title.ttf");

        title_alert.setTypeface(tf);

        add_place_marker_dialog.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {









                id_place_key = UUID.randomUUID().toString();
                String name_place = name_water_object.getText().toString();
                String water_object = getIntent().getStringExtra("name");

                if (name_place.isEmpty()) {

                    Toast.makeText(PlacesUserMapActivity.this, R.string.empty_name, Toast.LENGTH_SHORT).show();

                    return;
                }
                if (!RegistrationActivity.isNameValid(name_water_object.getText().toString())) {

                    Toast.makeText(PlacesUserMapActivity.this, R.string.not_valid_name, Toast.LENGTH_SHORT).show();

                    return;
                }


                Places place_info = new Places(name_place,
                        lat,
                        lon,
                        (double) zoom,
                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        water_object,
                        id_place_key);

                System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());

                FirebaseDatabase.getInstance().getReference("Places").child(id_place_key).setValue(place_info);

                placesUserActivity.AddDataWater(place_info);

                Bundle bundle = new Bundle();
                bundle.putString("water_object", water_object);
                Intent intent = new Intent();
                intent.putExtras(bundle);


                setResult(PlacesUserActivity.RESULT_OK, intent);


                finish();


            }
        });
        add_place_marker_dialog.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        add_place_marker_dialog.setCancelable(true);
        add_place_marker_dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, R.string.ups,
                        Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog alert11 = add_place_marker_dialog.create();
        alert11.getWindow().setBackgroundDrawableResource(R.color.orange);
        alert11.show();


        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        Button buttonbackground2 = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);

        // buttonbackground.setBackgroundColor(Color.BLUE);
        buttonbackground.setTextColor(context.getResources().getColor(R.color.colorWhite));
        buttonbackground2.setTextColor(context.getResources().getColor(R.color.colorWhite));




    }
    public void UpdatePlace(Double lat, Double lon, Float zom){

        this.googlemap = googlemap;
        this.placesUserActivity = new PlacesUserActivity();
        context = PlacesUserMapActivity.this;




        googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(lat, lon), zom));


        /*CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(zom)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googlemap.animateCamera(cameraUpdate);*/

    }



}
