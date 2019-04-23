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
import android.widget.TextView;

import com.company.imetlin.fishmarker.MapActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
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
    public ImageButton imageButton;
    public Menu menu;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtnameplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_user_activity);


        alldataplaces = new ArrayList<>();


        txtnameplace = (TextView) findViewById(R.id.txtnameplace);
        imageButton = (ImageButton) findViewById(R.id.add_place);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        final String txtName = getIntent().getStringExtra("name");


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


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Places place_information = dataSnapshot1.getValue(Places.class);
                    alldataplaces.add(place_information);
                    System.out.println(place_information);

                }
                setAdapter();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });





    }

    private void setAdapter(){


        final List<Places> image_details = getListData();

        System.out.println("ssss");

        adapter = new PlacesUserAdapter(image_details, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(PlacesUserActivity.this, MapActivity.class);
                Places m = image_details.get(position);

                intent.putExtra("latitude", m.getLatitude());
                intent.putExtra("longitude", m.getLongitude());
                intent.putExtra("zoom", m.getZoom());
                startActivity(intent);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private List<Places> getListData() {

        System.out.println(alldataplaces);
        System.out.println(alldataplaces);

        Intent intent = getIntent();
        String txtName = getIntent().getStringExtra("name");
        ArrayList<Places> list = new ArrayList<Places>();

        switch (txtName) {

            case "Ocean":
            case "Океан":

                for (Places place : alldataplaces){
                    if(place.getWaterobject().equals("Ocean")){
                        list.add(place);
                        txtnameplace.setVisibility(View.GONE);
                        System.out.println(place);
                    }
                }
/////////////////////
                break;

            case "Sea":
            case "Море":
                for (Places place : alldataplaces){
                    if(place.getWaterobject().equals("Sea")){
                        list.add(place);
                        txtnameplace.setVisibility(View.GONE);
                        System.out.println(place);
                    }
                }
/////////////////////
                break;

            case "Lake":
            case "Озеро":
                for (Places place : alldataplaces){
                    if(place.getWaterobject().equals("Lake")){
                        list.add(place);
                        txtnameplace.setVisibility(View.GONE);
                        System.out.println(place);
                    }
                }
/////////////////////
                break;

            case "River":
            case "Река":
                for (Places place : alldataplaces){
                    if(place.getWaterobject().equals("River")){
                        list.add(place);
                        txtnameplace.setVisibility(View.GONE);
                        System.out.println(place);
                    }
                }
/////////////////////
                break;

            case "Gulf":
            case "Залив":
                for (Places place : alldataplaces){
                    if(place.getWaterobject().equals("Gulf")){
                        list.add(place);
                        txtnameplace.setVisibility(View.GONE);
                        System.out.println(place);
                    }
                }
/////////////////////
                break;

            case "Another":
            case "Другое":
                for (Places place : alldataplaces){
                    if(place.getWaterobject().equals("Another")){
                        list.add(place);
                        txtnameplace.setVisibility(View.GONE);
                        System.out.println(place);
                    }
                }
////////////////////
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


