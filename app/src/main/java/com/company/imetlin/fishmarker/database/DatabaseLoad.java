package com.company.imetlin.fishmarker.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import java.util.List;
import java.util.ListIterator;


import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID_PRIMARY;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LATITUDE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LONGITUDE;

public class DatabaseLoad {

    private SQLiteHelper sqLiteHelper;
    public Context context;

    private GoogleMap googlemap;
    private List<Marker> markers;
    public ArrayList<ModelClass> alldatamarkers;
    private AlertDialog alertDialog;
    public Integer last_id;
    public CardMarkerActivity cardMarkerActivity;


    private static DatabaseLoad instance;


    private DatabaseLoad() {

    }

    public static DatabaseLoad getInstance() {

        if (instance == null) {        //если объект еще не создан
            instance = new DatabaseLoad();    //создать новый объект
        }

        return instance;
    }
    public void  setContext(Context context) {

        this.context = context;


    }


    public void LoaderData(GoogleMap _googlemap) {

        this.markers = new ArrayList<Marker>();
        this.alldatamarkers = new ArrayList<ModelClass>();

        this.googlemap = _googlemap;

        this.last_id = 1;

        sqLiteHelper = new SQLiteHelper(context);

        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();


        this.cardMarkerActivity = cardMarkerActivity;

        //String text1 = getInstance(context).getResources().getString(R.string.lat_c);


        Cursor cursor = database.query(SQLiteHelper.DB_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {


            int idindex = cursor.getColumnIndex(DB_COL_ID_PRIMARY);

            double latitudeIndex = cursor.getColumnIndex(DB_COL_LATITUDE);
            double longitudeIndex = cursor.getColumnIndex(DB_COL_LONGITUDE);

            String titleIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_TITLE));

            String dateIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DATE));

            String depthIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DEPTH));
            String amountIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_AMOUNT));
            String noteIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_NOTE));

            do {


                Integer id = cursor.getInt(idindex);
                this.last_id = id;

                Double lat = cursor.getDouble((int) latitudeIndex);
                Double lon = cursor.getDouble((int) longitudeIndex);
                String title = cursor.getString(Integer.parseInt(titleIndex));
                String date = cursor.getString(Integer.parseInt(dateIndex));

                Integer depth = Integer.valueOf(cursor.getString(Integer.parseInt(depthIndex)));
                Integer amount = Integer.valueOf(cursor.getString(Integer.parseInt(amountIndex)));
                String note = cursor.getString(Integer.parseInt(noteIndex));

                ModelClass modelClass = new ModelClass(id, lat, lon, title, date, depth, amount, note);


                this.alldatamarkers.add(modelClass);


                CreateMarker(id, lat, lon, title);


            } while (cursor.moveToNext());
        } else {
            Toast.makeText(context, R.string.base, Toast.LENGTH_LONG).show();
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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fishmarker))
                .zIndex(identificator));
        //marker.setDraggable(true);

        markers.add(marker);

        LongClickOnMarker();


    }

    /*FUNCTION UPDATE MARKER*/


    public void UpdateMarker(ModelClass modelclass) {


        ListIterator<ModelClass> iterator = alldatamarkers.listIterator();
        while (iterator.hasNext()) {
            ModelClass next = iterator.next();
            if (next.getId() == modelclass.getId()) {

                iterator.set(modelclass);
                break;
            }
        }

        for (Marker marker : markers) {
            if (modelclass.getId() == marker.getZIndex()) {
                marker.setTitle(modelclass.getTitle());
                break;
            }
        }
        System.out.println("GOOD");
        // update marker in markers cycle
        googlemap.clear();

        ArrayList<Marker> markers_array = new ArrayList<Marker>();

        for (Marker marker : markers) {

            Marker marker_update = googlemap.addMarker(new MarkerOptions()
                    .position(marker.getPosition())
                    .title(marker.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fishmarker))
                    .zIndex(marker.getZIndex()));

            markers_array.add(marker_update);
        }

        markers = new ArrayList<Marker>(markers_array);

        LongClickOnMarker();
    }


    /*FUNCTION DELETE MARKER*/


    public void DeleteMarker(ModelClass modelclass) {

        ListIterator<ModelClass> iterator = alldatamarkers.listIterator();

        while (iterator.hasNext()) {
            ModelClass next = iterator.next();
            if (next.getId() == modelclass.getId()) {
                iterator.remove();
                break;
            }
        }

        for (Marker marker : markers) {
            if (modelclass.getId() == marker.getZIndex()) {
                // marker.remove(markers);\

                markers.remove(marker);
                break;
            }
        }
        googlemap.clear();

        ArrayList<Marker> markers_array = new ArrayList<Marker>();

        for (Marker marker : markers) {

            Marker marker_delete = googlemap.addMarker(new MarkerOptions()
                    .position(marker.getPosition())
                    .title(marker.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fishmarker))
                    .zIndex(marker.getZIndex()));

            markers_array.add(marker_delete);
        }

        markers = new ArrayList<Marker>(markers_array);

        LongClickOnMarker();
    }

    /*LONG CLICK ON MARKER*/

    public void LongClickOnMarker() {

        googlemap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markers) {
            @Override
            public void onLongClickListener(Marker marker) {

                for (final ModelClass modelClass : alldatamarkers) {
                    if (modelClass.getId() == (int) marker.getZIndex()) {
                        // Bingo!
                        alertDialog = new AlertDialog.Builder(context).create();


                        alertDialog.setTitle(R.string.complete_c);
                        alertDialog.setMessage(context.getResources().getString(R.string.lat_c) + " " + modelClass.getLatitude() + "\n" +
                                context.getResources().getString(R.string.lon_c) + " " + modelClass.getLongitude() + "\n" +
                                context.getResources().getString(R.string.tit_c) + " " + modelClass.getTitle() + "\n" +
                                context.getResources().getString(R.string.date_c) + " " + modelClass.getDate() + "\n" +
                                context.getResources().getString(R.string.depth_c) + " " + modelClass.getDepth() + "\n" +
                                context.getResources().getString(R.string.amount_c) + " " + modelClass.getAmount() + "\n" +
                                context.getResources().getString(R.string.note_c) + " " + modelClass.getNote());
                        //last_id = modelClass.getId();

                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, context.getResources().getString(R.string.edit), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, R.string.update, Toast.LENGTH_LONG).show();


                                /////UPDATE MARKER IN BASE

                                int id_marker = modelClass.getId();

                                cardMarkerActivity = new CardMarkerActivity();
                                Bundle bundle = new Bundle();
                                bundle.putString("1", String.valueOf(modelClass.getLatitude()));
                                bundle.putString("2", String.valueOf(modelClass.getLongitude()));
                                bundle.putString("3", modelClass.getTitle());
                                bundle.putString("4", modelClass.getDate());
                                bundle.putString("5", String.valueOf(modelClass.getDepth()));
                                bundle.putString("6", String.valueOf(modelClass.getAmount()));
                                bundle.putString("7", modelClass.getNote());
                                bundle.putString("8", String.valueOf(id_marker));

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

    public boolean SearchMarker(Double lat, Double lon) {

        for (Marker marker : markers) {
            if ((marker.getPosition().latitude == lat) &&
                    (marker.getPosition().longitude == lon)) {
                return true;
            }
        }
        return false;

    }


}


