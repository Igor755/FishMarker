package com.company.imetlin.fishmarker.userplaces;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.MapActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
import com.company.imetlin.fishmarker.pojo.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.VISIBLE;

public class PlacesUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlacesUserAdapter adapter;
    private List<Places> listItems;
    public static ArrayList<Places> alldataplaces;
    public ImageButton imageButton;
    public Menu menu;
    private RecyclerView.LayoutManager mLayoutManager;
    public static TextView txtnameplace;
    public List<Places> image_details;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    private int mGridViewBGColor1 = Color.parseColor("#cce6ff");
    private int mGridViewBGColor2 = Color.parseColor("#FFFefd");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_user_activity);



        mAuth = FirebaseAuth.getInstance();

        alldataplaces = new ArrayList<>();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(VISIBLE);


        txtnameplace = (TextView) findViewById(R.id.txtnameplace);
        imageButton = (ImageButton) findViewById(R.id.add_place);


        Intent intent = getIntent();
        final String txtName = getIntent().getStringExtra("name");

        txtnameplace.setVisibility(VISIBLE);






        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacesUserActivity.this, PlacesUserMapActivity.class);
                intent.putExtra("name", txtName);
                startActivityForResult(intent, 1);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query myRef = database.getReference("Places").orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Places place_information = dataSnapshot1.getValue(Places.class);
                    alldataplaces.add(place_information);
                    System.out.println(place_information);

                }
                setAdapter(txtName);
                System.out.println("25");


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


    }

    private void setAdapter(String txt) {

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        //Intent intent = getIntent();
        //String txtNameAdapter = getIntent().getStringExtra("name");

        image_details = getListData(txt);


        if (image_details.size() != 0) {
            txtnameplace.setVisibility(View.GONE);

        }




        adapter = new PlacesUserAdapter(image_details, getBaseContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {




                ColorDrawable[] colors = {
                        new ColorDrawable(mGridViewBGColor1), // Animation starting color
                        new ColorDrawable(mGridViewBGColor2) // Animation ending color
                };

                TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
                view.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);





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

    private List<Places> getListData(String txtName) {


        ArrayList<Places> list = new ArrayList<Places>();


        switch (txtName) {

            case "ocean":

                for (Places place : alldataplaces) {
                    if (place.getWaterobject().equals("ocean")) {
                        list.add(place);

                    }
                }
                Collections.sort(list);
                progressBar.setVisibility(View.INVISIBLE);
                break;
//////////////////////////////////////////////////////////////
            case "sea":
                for (Places place : alldataplaces) {
                    if (place.getWaterobject().equals("sea")) {
                        list.add(place);

                    }
                }
                Collections.sort(list);
                progressBar.setVisibility(View.INVISIBLE);
                break;
//////////////////////////////////////////////////////////////
            case "lake":
                for (Places place : alldataplaces) {
                    if (place.getWaterobject().equals("lake")) {
                        list.add(place);


                    }
                }
                Collections.sort(list);
                progressBar.setVisibility(View.INVISIBLE);
                break;
//////////////////////////////////////////////////////////////
            case "river":
                for (Places place : alldataplaces) {
                    if (place.getWaterobject().equals("river")) {
                        list.add(place);

                    }
                }
                Collections.sort(list);
                progressBar.setVisibility(View.INVISIBLE);
                break;
//////////////////////////////////////////////////////////////
            case "gulf":
                for (Places place : alldataplaces) {
                    if (place.getWaterobject().equals("gulf")) {
                        list.add(place);
                    }
                }
                Collections.sort(list);
                progressBar.setVisibility(View.INVISIBLE);
                break;
//////////////////////////////////////////////////////////////
            case "another":
                for (Places place : alldataplaces) {
                    if (place.getWaterobject().equals("another")) {
                        list.add(place);

                    }
                }
                Collections.sort(list);
                progressBar.setVisibility(View.INVISIBLE);
                break;
//////////////////////////////////////////////////////////////

            default:
                progressBar.setVisibility(View.INVISIBLE);
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

    public void AddDataWater(Places water) {

        String water_object = water.getWaterobject();
        alldataplaces.add(water);
        // getListData(water_object);
        // setAdapter(water_object);
        // image_details = getListData(water_object);
        // adapter.setItems(image_details);
        // recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == PlacesUserActivity.RESULT_OK) {


                String name_water_object = data.getStringExtra("water_object");
                setAdapter(name_water_object);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            if (resultCode == PlacesUserActivity.RESULT_CANCELED) {
                //Write your code if there's no result

                Toast.makeText(getBaseContext(), R.string.cancel, Toast.LENGTH_SHORT)
                        .show();
            }

        }
        }
    }


