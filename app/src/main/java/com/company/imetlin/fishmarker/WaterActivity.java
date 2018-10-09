package com.company.imetlin.fishmarker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.company.imetlin.fishmarker.adapters.AdapterRecycler;
import com.company.imetlin.fishmarker.pojo.ItemTwoActivity;

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
        List<ItemTwoActivity> image_details = getListData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);

        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mAdapter = new AdapterRecycler(image_details);
        mRecyclerView.setAdapter(mAdapter);
    }


    private List<ItemTwoActivity> getListData(){

        String txtName = getIntent().getStringExtra("name");
        List<ItemTwoActivity> list = new ArrayList<ItemTwoActivity>();

        switch (txtName){
            case "Ocean":

                ItemTwoActivity Black = new ItemTwoActivity("Black");
                list.add(Black);

        }
        return list;


       /* String[] mDataSet = new String[100];
        for (int i = 0; i < 100; i++) {
            mDataSet[i] = "item" + i;
        }
        return mDataSet;*/
    }

}