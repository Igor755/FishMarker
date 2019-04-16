package com.company.imetlin.fishmarker.userplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.pojo.Places;

import java.util.ArrayList;
import java.util.List;

public class PlacesUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlacesUserAdapter adapter;
    private List<Places> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_user_activity);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        //Generate sample data

        for (int i = 0; i<10; i++) {
            listItems.add(new Places("Dnepr",12.232,12.2,2));
        }

        //Set adapter
        adapter = new PlacesUserAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }
}


