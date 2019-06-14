package com.company.imetlin.fishmarker.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.company.imetlin.fishmarker.CardMarkerActivity;
import com.company.imetlin.fishmarker.MapActivity;
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

    private MapActivity mapActivity;
    private Drawable drawable;
    private AlertDialog.Builder alert_detail;

    /////////////////////////////////////////////////ALERT DIALOG

    private TextView latitude;
    private TextView longitude;
    private TextView title_marker;
    private TextView date;
    private TextView depth;
    private TextView amount;
    private TextView note;
    private TextView title_alert;






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

        final Drawable my_icons_fish = res.getDrawable(R.drawable.fish_my_30);
        final Drawable another_icons_fish = res.getDrawable(R.drawable.fish_another_30);


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

    @SuppressLint("SetTextI18n")
    public void DetailMarker(Marker detailmarker) {

        for (final MarkerInformation modelClass : alldatamarkers) {
            if (modelClass.getLatitude() == detailmarker.getPosition().latitude &&
                    modelClass.getLongitude() == detailmarker.getPosition().longitude) {


                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.detail_marker_dialog, null);
                alert_detail = new AlertDialog.Builder(context);


                alert_detail.setView(promptsView);


                title_alert = (TextView) promptsView.findViewById(R.id.title_alert);
                latitude = (TextView) promptsView.findViewById(R.id.latitude_alert);
                longitude = (TextView) promptsView.findViewById(R.id.longitude_alert);
                title_marker = (TextView) promptsView.findViewById(R.id.title);
                date = (TextView) promptsView.findViewById(R.id.date);
                depth = (TextView) promptsView.findViewById(R.id.depth);
                amount = (TextView) promptsView.findViewById(R.id.amount);
                note = (TextView) promptsView.findViewById(R.id.note);

                Typeface tf = Typeface.createFromAsset(context.getAssets(), "alert_font_title.ttf");

                title_alert.setTypeface(tf);


                latitude.setText(latitude.getText() + " " + modelClass.getLatitude());
                longitude.setText(longitude.getText() + " " + modelClass.getLongitude());
                title_marker.setText(title_marker.getText() + " " + modelClass.getTitle());
                date.setText(date.getText() + " " + modelClass.getDate());
                depth.setText(depth.getText() + " " + modelClass.getDepth());
                amount.setText(amount.getText() + " " + modelClass.getAmount());
                note.setText(note.getText() + " " + modelClass.getNote());

                /*
                alertDialog.setTitle(R.string.complete_c);
                alertDialog.setMessage(context.getResources().getString(R.string.lat_c) + " " + modelClass.getLatitude() + "\n" +
                        context.getResources().getString(R.string.lon_c) + " " + modelClass.getLongitude() + "\n" +
                        context.getResources().getString(R.string.tit_c) + " " + modelClass.getTitle() + "\n" +
                        context.getResources().getString(R.string.date_c) + " " + modelClass.getDate() + "\n" +
                        context.getResources().getString(R.string.depth_c) + " " + modelClass.getDepth() + "\n" +
                        context.getResources().getString(R.string.amount_c) + " " + modelClass.getAmount() + "\n" +
                        context.getResources().getString(R.string.note_c) + " " + modelClass.getNote());*/

                alert_detail.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });

                AlertDialog alert11 = alert_detail.create();
                alert11.getWindow().setBackgroundDrawableResource(R.color.orange);
                alert11.show();

                Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_POSITIVE);

                // buttonbackground.setBackgroundColor(Color.BLUE);
                buttonbackground.setTextColor(context.getResources().getColor(R.color.colorWhite));
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

        int my_ic = R.drawable.fish_my_30;

        ArrayList<Marker> markers_array = new ArrayList<Marker>();

        ListIterator<MarkerInformation> iterator = alldatamarkers.listIterator();

        while (iterator.hasNext()) {

            MarkerInformation next = iterator.next();

            if (!next.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                my_ic = R.drawable.fish_another_30;
            }
            else {
                my_ic = R.drawable.fish_my_30;
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

            int my_ic = R.drawable.fish_my_30;

            ArrayList<Marker> markers_array = new ArrayList<Marker>();

            ListIterator<MarkerInformation> iterator = alldatamarkers.listIterator();
            while (iterator.hasNext()) {

                MarkerInformation next = iterator.next();

                if (!next.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    my_ic = R.drawable.fish_another_30;
                } else {
                    my_ic = R.drawable.fish_my_30;
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
