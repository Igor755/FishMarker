package com.company.imetlin.fishmarker.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
import java.util.List;

import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID_PRIMARY;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LATITUDE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LONGITUDE;

public class DatabaseLoad {

    private SQLiteHelper sqLiteHelper;
    public Context context;

    public GoogleMap googlemap;
    private List<Marker> markers;
    private Cursor cursor;
    public ArrayList<ModelClass> alldatamarkers;
    private AlertDialog alertDialog;
    public Integer last_id;


    private static DatabaseLoad instance;

    private DatabaseLoad() {

    }

    public static DatabaseLoad getInstance(Context context) {

        if (instance == null) {        //если объект еще не создан
            instance = new DatabaseLoad();    //создать новый объект
            instance.context = context;
        }

        return instance;
    }


    public void LoaderData(GoogleMap _googlemap) {

        this.googlemap = _googlemap;
        this.markers = new ArrayList<Marker>();
        alldatamarkers = new ArrayList<ModelClass>();
        this.last_id = -1;

        sqLiteHelper = new SQLiteHelper(context);

        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();


        Cursor cursor = database.query(SQLiteHelper.DB_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {


            int idindex = cursor.getColumnIndex(DB_COL_ID_PRIMARY);


            double longitudeIndex = cursor.getColumnIndex(DB_COL_LONGITUDE);
            double latitudeIndex = cursor.getColumnIndex(DB_COL_LATITUDE);
            String dateIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DATE));


            String depthIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DEPTH));
            String amountIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_AMOUNT));
            String noteIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_NOTE));

            do {


                Log.d("mlog", " long = " + cursor.getDouble((int) longitudeIndex) +
                        " lat = " + cursor.getDouble((int) latitudeIndex) +
                        " date = " + cursor.getString(Integer.parseInt(dateIndex)));


                Integer id = cursor.getInt(idindex);
                this.last_id = id;


                Double lon = cursor.getDouble((int) longitudeIndex);
                Double lat = cursor.getDouble((int) latitudeIndex);
                String title = cursor.getString(Integer.parseInt(dateIndex));

                Double depth = Double.valueOf((cursor.getString(Integer.parseInt(depthIndex))));
                Integer amount = Integer.valueOf(cursor.getString(Integer.parseInt(amountIndex)));
                String note = cursor.getString(Integer.parseInt(noteIndex));

                ModelClass modelClass = new ModelClass(id, lat, lon, title, depth, amount, note);


                alldatamarkers.add(modelClass);


                CreateMarker(id, lat, lon, title);


            } while (cursor.moveToNext());
        } else {
            Toast.makeText(context, "Base EMPTY", Toast.LENGTH_LONG).show();
        }

        cursor.close();
        database.close();
    }

    public void CreateMarker(Integer ident, final double _lat, final double _lon, String c) {

        Marker marker = googlemap.addMarker(new MarkerOptions()
                .position(new LatLng(_lat, _lon))
                .title(c)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico))
                .zIndex(ident));
        //marker.setDraggable(true);

        markers.add(marker);

        googlemap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markers) {
            @Override
            public void onLongClickListener(Marker marker) {

                //DatabaseLoad load = DatabaseLoad.getInstance(null);
                //System.out.println(load.alldatamarkers);

                for (ModelClass modelClass : alldatamarkers) {
                    if (modelClass.getId() == (int) marker.getZIndex()) {
                        // Bingo!

                        alertDialog = new AlertDialog.Builder(context).create();

                        alertDialog.setTitle("Сomplete marker information");
                        alertDialog.setMessage(modelClass.getLongitude() + "\n" +
                                modelClass.getLatitude() + "\n" +
                                modelClass.getDate() + "\n" +
                                modelClass.getDepth() + "\n" +
                                modelClass.getAmount() + "\n" +
                                modelClass.getNote());
                        last_id = modelClass.getId();

                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });

                        alertDialog.show();
                        break;
                    }
                }
            }
        });


    }


}


