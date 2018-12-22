package com.company.imetlin.fishmarker.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.company.imetlin.fishmarker.CardMarkerActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.myinterfaces.LinkMarkerLongClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID_PRIMARY;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LATITUDE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LONGITUDE;

public class DatabaseLoad {

    private SQLiteHelper sqLiteHelper;
    public Context context;

    private GoogleMap googlemap;
    private List<Marker> markers;
    private Cursor cursor;
    public ArrayList<ModelClass> alldatamarkers;
    private AlertDialog alertDialog;
    public Integer last_id;
    public CardMarkerActivity cardMarkerActivity;


    private static DatabaseLoad instance;

    private DatabaseLoad() {

        this.markers = new ArrayList<Marker>();
        this.alldatamarkers = new ArrayList<ModelClass>();
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

        this.last_id = 0;

        sqLiteHelper = new SQLiteHelper(context);

        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();


        this.cardMarkerActivity = cardMarkerActivity;


        Cursor cursor = database.query(SQLiteHelper.DB_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {


            int idindex = cursor.getColumnIndex(DB_COL_ID_PRIMARY);

            double latitudeIndex = cursor.getColumnIndex(DB_COL_LATITUDE);
            double longitudeIndex = cursor.getColumnIndex(DB_COL_LONGITUDE);

            String dateIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DATE));

            String depthIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DEPTH));
            String amountIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_AMOUNT));
            String noteIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_NOTE));

            do {


           /*     Log.d("mlog", " long = " + cursor.getDouble((int) longitudeIndex) +
                        " lat = " + cursor.getDouble((int) latitudeIndex) +
                        " date = " + cursor.getString(Integer.parseInt(dateIndex)));*/


                Integer id = cursor.getInt(idindex);
                this.last_id = id;

                Double lat = cursor.getDouble((int) latitudeIndex);
                Double lon = cursor.getDouble((int) longitudeIndex);
                String title = cursor.getString(Integer.parseInt(dateIndex));

                Integer depth = Integer.valueOf(cursor.getString(Integer.parseInt(depthIndex)));
                Integer amount = Integer.valueOf(cursor.getString(Integer.parseInt(amountIndex)));
                String note = cursor.getString(Integer.parseInt(noteIndex));

                ModelClass modelClass = new ModelClass(id, lat, lon, title, depth, amount, note);


                this.alldatamarkers.add(modelClass);


                CreateMarker(id, lat, lon, title);


            } while (cursor.moveToNext());
        } else {
            Toast.makeText(context, "Base EMPTY", Toast.LENGTH_LONG).show();
        }

        cursor.close();
        database.close();
    }

    public void AddDataMarker(ModelClass marker) {

        this.alldatamarkers.add(marker);
        this.last_id++;
    }

    public void CreateMarker(final Integer identificator, final double _lat, final double _lon, String title_marker) {

        Marker marker = googlemap.addMarker(new MarkerOptions()
                .position(new LatLng(_lat, _lon))
                .title(title_marker)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico))
                .zIndex(identificator));
        //marker.setDraggable(true);

        markers.add(marker);

        LongClickOnMarker();


    }
    /*
    FUNCTION UPDATE MARKER*/


    public void UpdateMarker(ModelClass modelclass){


        ListIterator<ModelClass> iterator = alldatamarkers.listIterator();
        while (iterator.hasNext()) {
            ModelClass next = iterator.next();
            if (next.getId() == modelclass.getId())
            {

                iterator.set(modelclass);
                break;
            }
        }

        for (Marker marker : markers){
           if (modelclass.getId() == marker.getZIndex()){
               marker.setTitle(modelclass.getDate());
               break;
           }
        }
        System.out.println("GOOD");
        // update marker in markers cycle
        googlemap.clear();

        ArrayList<Marker> markers_array = new ArrayList<Marker>();

        for (Marker marker:markers){

            Marker marker_update = googlemap.addMarker(new MarkerOptions()
                    .position(marker.getPosition())
                    .title(marker.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico))
                    .zIndex(marker.getZIndex()));

            markers_array.add(marker_update);
        }

        markers = new ArrayList<Marker>(markers_array);

        LongClickOnMarker();
    }


/*FUNCTION DELETE MARKER*/


    public void DeleteMarker(ModelClass modelclass){

        ListIterator<ModelClass> iterator = alldatamarkers.listIterator();

        while (iterator.hasNext()) {
            ModelClass next = iterator.next();
            if (next.getId() == modelclass.getId())
            {
                iterator.remove();
                break;
            }
        }

        for (Marker marker : markers){
            if (modelclass.getId() == marker.getZIndex()){
               // marker.remove(markers);\

                markers.remove(marker);
                break;
            }
        }
        googlemap.clear();

        ArrayList<Marker> markers_array = new ArrayList<Marker>();

        for (Marker marker:markers){

            Marker marker_delete = googlemap.addMarker(new MarkerOptions()
                    .position(marker.getPosition())
                    .title(marker.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico))
                    .zIndex(marker.getZIndex()));

            markers_array.add(marker_delete);
        }

        markers = new ArrayList<Marker>(markers_array);

        LongClickOnMarker();
    }

    public void LongClickOnMarker(){

        googlemap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markers) {
            @Override
            public void onLongClickListener(Marker marker) {

                for (final ModelClass modelClass : alldatamarkers) {
                    if (modelClass.getId() == (int) marker.getZIndex()) {
                        // Bingo!

                        alertDialog = new AlertDialog.Builder(context).create();

                        alertDialog.setTitle("Сomplete marker information");
                        alertDialog.setMessage(modelClass.getLatitude() + "\n" +
                                modelClass.getLongitude() + "\n" +
                                modelClass.getDate() + "\n" +
                                modelClass.getDepth() + "\n" +
                                modelClass.getAmount() + "\n" +
                                modelClass.getNote());
                        //last_id = modelClass.getId();

                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "EDIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "UPDATE", Toast.LENGTH_LONG).show();


                                /////UPDATE MARKER IN BASE

                                int id_marker = modelClass.getId();

                                cardMarkerActivity = new CardMarkerActivity();
                                Bundle bundle = new Bundle();
                                bundle.putString("1", String.valueOf(modelClass.getLatitude()));
                                bundle.putString("2", String.valueOf(modelClass.getLongitude()));
                                bundle.putString("3", modelClass.getDate());
                                bundle.putString("4", String.valueOf(modelClass.getDepth()));
                                bundle.putString("5", String.valueOf(modelClass.getAmount()));
                                bundle.putString("6", modelClass.getNote());
                                bundle.putString("7", String.valueOf(id_marker));

                                Intent intent = new Intent(DatabaseLoad.instance.context, CardMarkerActivity.class);

                                System.out.println(bundle);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

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


