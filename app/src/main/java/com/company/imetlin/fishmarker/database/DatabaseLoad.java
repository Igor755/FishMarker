package com.company.imetlin.fishmarker.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.company.imetlin.fishmarker.CardMarkerActivity;
import com.company.imetlin.fishmarker.MapActivity;
import com.company.imetlin.fishmarker.Navigation.NavigationBar;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.myinterfaces.LinkMarkerLongClickListener;
import com.company.imetlin.fishmarker.pojo.MarkerInformation;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicLong;


import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_ID_PRIMARY;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LATITUDE;
import static com.company.imetlin.fishmarker.database.SQLiteHelper.DB_COL_LONGITUDE;

public class DatabaseLoad {

    public Context context;

    private GoogleMap googlemap;
    private List<Marker> markers;
    public ArrayList<MarkerInformation> alldatamarkers;
    private AlertDialog alertDialog;
    private CardMarkerActivity cardMarkerActivity;


    private static DatabaseLoad instance;


    private DatabaseLoad() {

    }

    public static DatabaseLoad getInstance() {

        if (instance == null) {        //если объект еще не создан
            instance = new DatabaseLoad();    //создать новый объект
        }

        return instance;
    }

    public void setContext(Context context) {

        this.context = context;


    }


    public void LoaderData(GoogleMap _googlemap) {

        this.alldatamarkers = new ArrayList<MarkerInformation>();
        this.markers = new ArrayList<Marker>();
        this.googlemap = _googlemap;
        this.cardMarkerActivity = cardMarkerActivity;


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Markers");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    MarkerInformation markerInformation = dataSnapshot1.getValue(MarkerInformation.class);
                    alldatamarkers.add(markerInformation);
                    System.out.println(markerInformation);

                }


                for (int i = 0; i < alldatamarkers.size(); i++) {

                    //if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(alldatamarkers.get(i).getUid())) {
                    double latitude = alldatamarkers.get(i).getLatitude();
                    double longitude = alldatamarkers.get(i).getLongitude();
                    String tittle = alldatamarkers.get(i).getTitle();
//                    Integer id_marker = Integer.valueOf(alldatamarkers.get(i).getMarker_id());
                    CreateMarker(latitude, longitude, tittle);

                }
                System.out.println(alldatamarkers);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


    }

    public void CreateMarker(final double _lat, final double _lon, String title_marker) {

        Marker marker = googlemap.addMarker(new MarkerOptions()
                .position(new LatLng(_lat, _lon))
                .title(title_marker)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fishmarker)));


        markers.add(marker);


        // LongClickOnMarker();

    }

    public void AddDataMarker(MarkerInformation marker) {

        this.alldatamarkers.add(marker);

    }

    public void DetailMarker(Marker detailmarker) {

        for (final MarkerInformation modelClass : alldatamarkers) {
            if (modelClass.getLatitude() == detailmarker.getPosition().latitude &&
                    modelClass.getLongitude() == detailmarker.getPosition().longitude) {
                alertDialog = new AlertDialog.Builder(context).create();


                alertDialog.setTitle(R.string.complete_c);
                alertDialog.setMessage(context.getResources().getString(R.string.lat_c) + " " + modelClass.getLatitude() + "\n" +
                        context.getResources().getString(R.string.lon_c) + " " + modelClass.getLongitude() + "\n" +
                        context.getResources().getString(R.string.tit_c) + " " + modelClass.getTitle() + "\n" +
                        context.getResources().getString(R.string.date_c) + " " + modelClass.getDate() + "\n" +
                        context.getResources().getString(R.string.depth_c) + " " + modelClass.getDepth() + "\n" +
                        context.getResources().getString(R.string.amount_c) + " " + modelClass.getAmount() + "\n" +
                        context.getResources().getString(R.string.note_c) + " " + modelClass.getNote());

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
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

    public void UpdateMarkerOfMapActivity(Marker upadtemarker) {

        for (final MarkerInformation modelClass : alldatamarkers) {
            if (modelClass.getLatitude() == upadtemarker.getPosition().latitude &&
                    modelClass.getLongitude() == upadtemarker.getPosition().longitude) {

                cardMarkerActivity = new CardMarkerActivity();
                Bundle bundle = new Bundle();
                bundle.putString("1", modelClass.getUid());
                bundle.putString("2", modelClass.getId_marker_key());
                bundle.putString("3", String.valueOf(modelClass.getLatitude()));
                bundle.putString("4", String.valueOf(modelClass.getLongitude()));
                bundle.putString("5", modelClass.getTitle());
                bundle.putString("6", modelClass.getDate());
                bundle.putString("7", String.valueOf(modelClass.getDepth()));
                bundle.putString("8", String.valueOf(modelClass.getAmount()));
                bundle.putString("9", modelClass.getNote());


                Intent intent = new Intent(DatabaseLoad.instance.context, CardMarkerActivity.class);

                System.out.println(bundle);
                intent.putExtras(bundle);
                context.startActivity(intent);


            }

        }
    }



    public void UpdateMarker(MarkerInformation updatemarker) {


        ListIterator<MarkerInformation> iterator = alldatamarkers.listIterator();
        while (iterator.hasNext()) {
            MarkerInformation next = iterator.next();
            if (next.getLatitude().equals(updatemarker.getLatitude()) &&
                    next.getLongitude().equals(updatemarker.getLongitude())) {

                iterator.set(updatemarker);
                break;
            }
        }

        for (Marker marker : markers) {
            if (updatemarker.getLatitude() == marker.getPosition().latitude  &&
                    updatemarker.getLongitude() == marker.getPosition().longitude) {
                marker.setTitle(updatemarker.getTitle());
                break;
            }
        }
        System.out.println("GOOD");

        //update marker in markers cycle

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

        System.out.println("GOOD");


        //DatabaseLoad.getInstance().setContext(context);


    }


    /*FUNCTION DELETE MARKER*/



        public void DeleteMarker(MarkerInformation deletemarker){

            ListIterator<MarkerInformation> iterator = alldatamarkers.listIterator();

            while (iterator.hasNext()) {
                MarkerInformation next = iterator.next();
                if (next.getLatitude().equals(deletemarker.getLatitude()) &&
                        next.getLongitude().equals(deletemarker.getLongitude())) {
                    iterator.remove();
                    break;
                }
            }

            for (Marker marker : markers) {
                if (deletemarker.getLatitude() == marker.getPosition().latitude  &&
                        deletemarker.getLongitude() == marker.getPosition().longitude) {

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


        }
    public boolean SearchMarker (Double lat, Double lon){

        for (Marker marker : markers) {
            if ((marker.getPosition().latitude == lat) &&
                    (marker.getPosition().longitude == lon)) {
                return true;
            }
        }
        return false;

    }




}
    /*private void LongClickOnMarker() {

        googlemap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markers) {
            @Override
            public void onLongClickListener(Marker marker) {


                System.out.println(2);
                System.out.println(2);

                for (final MarkerInformation modelClass : alldatamarkers) {
                    System.out.println(32);
                    if (modelClass.getMarker_id() == String.valueOf(marker.getZIndex())) {

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

                                String id_marker = modelClass.getMarker_id();

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


    }*/


/*

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

            double depthIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_DEPTH);
            String amountIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_AMOUNT));
            String noteIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_NOTE));

            do {


                Integer id = cursor.getInt(idindex);
                this.last_id = id;

                Double lat = cursor.getDouble((int) latitudeIndex);
                Double lon = cursor.getDouble((int) longitudeIndex);
                String title = cursor.getString(Integer.parseInt(titleIndex));
                String date = cursor.getString(Integer.parseInt(dateIndex));

                Double depth = cursor.getDouble((int) depthIndex);
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
        public void AddDataMarker (ModelClass marker){

            this.alldatamarkers.add(marker);
            this.last_id++;
        }


        public void CreateMarker ( final Integer identificator, final double _lat,
        final double _lon, String title_marker){

            Marker marker = googlemap.addMarker(new MarkerOptions()
                    .position(new LatLng(_lat, _lon))
                    .title(title_marker)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fishmarker))
                    .zIndex(identificator));
            //marker.setDraggable(true);

            markers.add(marker);

            LongClickOnMarker();


        }


        */
/*FUNCTION UPDATE MARKER*//*




    public void UpdateMarker(ModelClass modelclass) {


        ListIterator<ModelClass> iterator = alldatamarkers.listIterator();
        while (iterator.hasNext()) {
            ModelClass next = iterator.next();
            if (next.getModel_id() == modelclass.getModel_id()) {

                iterator.set(modelclass);
                break;
            }
        }

        for (Marker marker : markers) {
            if (modelclass.getModel_id() == marker.getZIndex()) {
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



*/
/*FUNCTION DELETE MARKER*//*



        public void DeleteMarker(ModelClass modelclass){

            ListIterator<ModelClass> iterator = alldatamarkers.listIterator();

            while (iterator.hasNext()) {
                ModelClass next = iterator.next();
                if (next.getModel_id() == modelclass.getModel_id()) {
                    iterator.remove();
                    break;
                }
            }

            for (Marker marker : markers) {
                if (modelclass.getModel_id() == marker.getZIndex()) {
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


        */
/*LONG CLICK ON MARKER*//*



        public void LongClickOnMarker () {

            googlemap.setOnMarkerDragListener(new LinkMarkerLongClickListener(markers) {
                @Override
                public void onLongClickListener(Marker marker) {

                    for (final ModelClass modelClass : alldatamarkers) {
                        if (modelClass.getModel_id() == (int) marker.getZIndex()) {
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

                                    int id_marker = modelClass.getModel_id();

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

        public boolean SearchMarker (Double lat, Double lon){

            for (Marker marker : markers) {
                if ((marker.getPosition().latitude == lat) &&
                        (marker.getPosition().longitude == lon)) {
                    return true;
                }
            }
            return false;

        }
    }




*/

