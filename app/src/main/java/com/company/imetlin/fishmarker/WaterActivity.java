package com.company.imetlin.fishmarker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.adapters.AdapterRecycler;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;

import java.util.ArrayList;
import java.util.List;


public class WaterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        //String[] myDataset = getDataSet();


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);

        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        final List<ModelClass> image_details = getListData();
        // создаем адаптер
        //mAdapter = new AdapterRecycler(image_details);

        mAdapter = new AdapterRecycler(image_details, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {






                Intent intent = new Intent(WaterActivity.this, MapActivity.class);
                ModelClass m = image_details.get(position);

                intent.putExtra("coordinates", m.getCoordinates());
                startActivity(intent);


            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    private List<ModelClass> getListData(){

        Intent intent = getIntent();
        String txtName = getIntent().getStringExtra("name");
        List<ModelClass> list = new ArrayList<ModelClass>();

        switch (txtName){
            case "Ocean":

                ModelClass Quiet = new ModelClass("Pacific",16.45031985,-165.30262298);
                ModelClass Atlantic = new ModelClass("Atlantic",32.60344946, -37.84536021);
                ModelClass Indian = new ModelClass("Indian",-22.0947353, 80.99712841);
                ModelClass Arctic = new ModelClass("Arctic",84.3581025,47.48212893);
                list.add(Quiet);
                list.add(Atlantic);
                list.add(Indian);
                list.add(Arctic);
                break;

            case "Sea":
                ModelClass Black = new ModelClass("Black",43.28405855,34.38411636);
                ModelClass Baltic = new ModelClass("Baltic",57.94983829,20.36551078);
                ModelClass Red = new ModelClass("Red",26.52140003,51.90711662);
                ModelClass Azov = new ModelClass("Azov",46.09045845,36.58556761);
                list.add(Black);
                list.add(Baltic);
                list.add(Red);
                list.add(Azov);

                break;

            case "Lake":
                ModelClass Baikal = new ModelClass("Baikal",53.18212452,107.98451206);
                ModelClass Victoria = new ModelClass("Victoria",-1.14207713, 32.9604879);
                ModelClass Titicaca  = new ModelClass("Titicaca",-15.85597851,-69.33705471);
                list.add(Baikal);
                list.add(Victoria);
                list.add(Titicaca);
                break;

            case "River":
                ModelClass Dnieper = new ModelClass("Dnieper",50.99943179, 30.51317977);
                ModelClass Dniester = new ModelClass("Dniester",46.41115415,30.26159589);
                ModelClass Southern_Bug = new ModelClass("Southern_Bug", 48.71731705,29.14673289);
                ModelClass Turunchuk  = new ModelClass("Turunchuk",46.50142953, 30.04802302);
                list.add(Dnieper);
                list.add(Dniester);
                list.add(Southern_Bug);
                list.add(Turunchuk);
                break;



            default:
                break;

        }
        return list;


       /* String[] mDataSet = new String[100];
        for (int i = 0; i < 100; i++) {
            mDataSet[i] = "item" + i;
        }
        return mDataSet;*/
    }

}