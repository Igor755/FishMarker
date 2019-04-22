package com.company.imetlin.fishmarker.userplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.pojo.MarkerInformation;
import com.company.imetlin.fishmarker.pojo.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlacesUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlacesUserAdapter adapter;
    private List<Places> listItems;
    public static ArrayList<Places> alldataplaces;
    ImageButton imageButton;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_user_activity);


        this.alldataplaces = new ArrayList<Places>();

        imageButton = (ImageButton) findViewById(R.id.add_place);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final String txtName = getIntent().getStringExtra("name");


        final List<Places> image_details = getListData();

        listItems = new ArrayList<>();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacesUserActivity.this, PlacesUserMapActivity.class);
                intent.putExtra("name", txtName);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query myRef = database.getReference("Places").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

       /* myRef.orderByChild("uid")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());*/
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Places place_information = dataSnapshot1.getValue(Places.class);
                    alldataplaces.add(place_information);
                    System.out.println(place_information);

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        //Generate sample data



/*
        adapter = new PlacesUserAdapter(image_details, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


           *//*     Intent intent = new Intent(PlacesUserActivity.this, MapActivity.class);
                Places m = image_details.get(position);

                intent.putExtra("latitude", m.getLatitude());
                intent.putExtra("longitude", m.getLongitude());
                intent.putExtra("zoom", m.getZoom());
                startActivity(intent);*//*


            }
        });*/
        //Set adapter
        adapter = new PlacesUserAdapter(alldataplaces, this);
        recyclerView.setAdapter(adapter);
    }

    private List<Places> getListData() {

        Intent intent = getIntent();
        String txtName = getIntent().getStringExtra("name");
        ArrayList<Places> list = new ArrayList<Places>();

        switch (txtName) {

            case "Ocean":
            case "Океан":

                System.out.println(alldataplaces);
////////////////////////////////
                break;

            case "Sea":
            case "Море":
////////////////////////////////
                break;

            case "Lake":
            case "Озеро":
////////////////////////////////
                break;

            case "River":
            case "Река":
//////////////////////////////////
                break;

            case "Gulf":
            case "Залив":
//////////////////////////
                break;

            case "Another":
            case "Другое":
//////////////////////
                break;


            default:
                break;
        }
        return list;
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


}


