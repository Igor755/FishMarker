package com.company.imetlin.fishmarker.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;


import com.company.imetlin.fishmarker.CardMarkerActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.pojo.MarkerInformation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;





public class DatabaseLoad {

    public Context context;

    private GoogleMap googlemap;
    private List<Marker> markers;
    public ArrayList<MarkerInformation> alldatamarkers;
    private AlertDialog alertDialog;
    private CardMarkerActivity cardMarkerActivity;
    private Drawable drawable;



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

        Resources res = context.getResources();

        final Drawable my_icons_fish = res.getDrawable(R.drawable.fish_another_40);
        final Drawable another_icons_fish = res.getDrawable(R.drawable.fish_another_2);


        final Bitmap bitmap_my = ((BitmapDrawable)my_icons_fish).getBitmap();
        final Bitmap bitmap_another = ((BitmapDrawable)another_icons_fish).getBitmap();

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
                    String uid = alldatamarkers.get(i).getUid();
//                    Integer id_marker = Integer.valueOf(alldatamarkers.get(i).getMarker_id());

                    if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                        CreateMarker(latitude, longitude, tittle, bitmap_my);

                    }else if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                        CreateMarker(latitude, longitude, tittle, bitmap_another);

                    }



                }
                System.out.println(alldatamarkers);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


    }

    public void CreateMarker(final double _lat, final double _lon, String title_marker, Bitmap icon_marker) {

        Marker marker = googlemap.addMarker(new MarkerOptions()
                .position(new LatLng(_lat, _lon))
                .title(title_marker)
                .icon(BitmapDescriptorFactory.fromBitmap(icon_marker)));


        markers.add(marker);




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


        googlemap.clear();

        int my_ic = R.drawable.fish_another_40;

        ArrayList<Marker> markers_array = new ArrayList<Marker>();

        ListIterator<MarkerInformation> iterator = alldatamarkers.listIterator();

        while (iterator.hasNext()) {

            MarkerInformation next = iterator.next();

            if (!next.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                my_ic = R.drawable.fish_another_2;
            }
            else {
                my_ic = R.drawable.fish_another_40;
            }

            if (next.getLatitude().equals(updatemarker.getLatitude()) &&
                    next.getLongitude().equals(updatemarker.getLongitude())) {

                iterator.set(updatemarker);

                Marker marker_update = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(updatemarker.getLatitude(), updatemarker.getLongitude()))
                        .title(updatemarker.getTitle())
                        .icon(BitmapDescriptorFactory.fromResource(my_ic)));

                markers_array.add(marker_update);
            }
            else {

                Marker marker_update = googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(next.getLatitude(), next.getLongitude()))
                        .title(next.getTitle())
                        .icon(BitmapDescriptorFactory.fromResource(my_ic)));

                markers_array.add(marker_update);
            }

        }

        markers = new ArrayList<Marker>(markers_array);
    }


    /*FUNCTION DELETE MARKER*/



        public void DeleteMarker(MarkerInformation deletemarker){

            googlemap.clear();

            int my_ic = R.drawable.fish_another_40;

            ArrayList<Marker> markers_array = new ArrayList<Marker>();

            ListIterator<MarkerInformation> iterator = alldatamarkers.listIterator();
            while (iterator.hasNext()) {

                MarkerInformation next = iterator.next();

                if (!next.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    my_ic = R.drawable.fish_another_2;
                } else {
                    my_ic = R.drawable.fish_another_40;
                }
                if (next.getLatitude().equals(deletemarker.getLatitude()) &&
                        next.getLongitude().equals(deletemarker.getLongitude())) {
                    iterator.remove();


                } else {

                    Marker marker_delete = googlemap.addMarker(new MarkerOptions()
                            .position(new LatLng(next.getLatitude(), next.getLongitude()))
                            .title(next.getTitle())
                            .icon(BitmapDescriptorFactory.fromResource(my_ic)));

                    markers_array.add(marker_delete);


                }
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
