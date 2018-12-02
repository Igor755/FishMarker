package com.company.imetlin.fishmarker.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.myinterfaces.LinkMarkerLongClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseLoad {

    private SQLiteHelper sqLiteHelper;
    private final Context context;
    private ModelClass modelClass;
    public GoogleMap googlemap;
    private List<Marker> markers;

    public DatabaseLoad(Context context) {
        this.context = context;
    }


    public void LoaderData(GoogleMap _googlemap) {

       // List<ModelClass> markerlist = new ArrayList<ModelClass>();
        this.googlemap = _googlemap;

        this.markers = new ArrayList<Marker>();



        sqLiteHelper = new SQLiteHelper(context);

        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();


        Cursor cursor = database.query(SQLiteHelper.DB_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            double longitudeIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_LONGITUDE);
            double latitudeIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_LATITUDE);
            String dateIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DATE));

            do {

                Log.d("mlog", " long = " + cursor.getDouble((int) longitudeIndex) +
                        " lat = " + cursor.getDouble((int) latitudeIndex) +
                        " date = " + cursor.getString(Integer.parseInt(dateIndex)));


                Double lon = cursor.getDouble((int) longitudeIndex);
                Double lat = cursor.getDouble((int) latitudeIndex);
                String title = cursor.getString(Integer.parseInt(dateIndex));


                CreateMarker(lon,lat,title);



               // Marker marker = DatabaseLoad.createMarker()



            } while (cursor.moveToNext());
        } else {
            Toast.makeText(context, "Base EMPTY", Toast.LENGTH_LONG).show();
        }


    }
    public void CreateMarker (double a,double b, String c){


        Marker marker = googlemap.addMarker(new MarkerOptions()
                .position(new LatLng(a, b))
                .title(c)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico)));
        marker.setDraggable(true);

        markers.add(marker);

        googlemap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markers) {
            @Override
            public void onLongClickListener(Marker marker) {

                Toast.makeText(context, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
                        Toast.LENGTH_LONG).show();

            }
        });


    }



}


