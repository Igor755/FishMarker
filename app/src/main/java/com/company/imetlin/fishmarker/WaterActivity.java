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
        final List<ItemTwoActivity> image_details = getListData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);

        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(this);

        // создаем адаптер
        //mAdapter = new AdapterRecycler(image_details);
        mAdapter = new AdapterRecycler(image_details, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
              /*  int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                String item = image_details.get(itemPosition);
                Object o = mRecyclerView.getChildLayoutPosition(view);
                ItemTwoActivity itemTwoActivity = (ItemTwoActivity) o;
               ItemTwoActivity itemtwoActivity = (ItemTwoActivity) p;
                Toast.makeText(getBaseContext(), itemTwoActivity.getName(), Toast.LENGTH_LONG).show();
*/




                Intent intent = new Intent(WaterActivity.this,MapActivity.class);
               // intent.putExtra(String.valueOf(view), position);
                startActivity(intent);


            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
      /*  mRecyclerView.setOnClickListener((View.OnClickListener) new AdapterRecycler(getListData(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

             *//*  Object o = mRecyclerView.;
                ItemTwoActivity itemtwoActivity = (ItemTwoActivity) o;
                Toast.makeText(WaterActivity.this, "Selected :"
                        + " " + itemtwoActivity.getName(), Toast.LENGTH_LONG).show();*//*
            }
        }));*/
    }


    private List<ItemTwoActivity> getListData(){

        String txtName = getIntent().getStringExtra("name");
        List<ItemTwoActivity> list = new ArrayList<ItemTwoActivity>();

        switch (txtName){
            case "Ocean":

                ItemTwoActivity Quiet = new ItemTwoActivity("Quiet");
                ItemTwoActivity Atlantic = new ItemTwoActivity("Atlantic");
                ItemTwoActivity Indian = new ItemTwoActivity("Indian");
                ItemTwoActivity Arctic = new ItemTwoActivity("Arctic");
                list.add(Quiet);
                list.add(Atlantic);
                list.add(Indian);
                list.add(Arctic);
                break;

            case "Sea":
                ItemTwoActivity Black = new ItemTwoActivity("Black");
                ItemTwoActivity Baltic = new ItemTwoActivity("Baltic");
                ItemTwoActivity Red = new ItemTwoActivity("Red");
                ItemTwoActivity Azov = new ItemTwoActivity("Azov");
                ItemTwoActivity Laptev = new ItemTwoActivity("Laptev");
                ItemTwoActivity Barentc = new ItemTwoActivity("Barentc");
                ItemTwoActivity Japanese = new ItemTwoActivity("Japanese");
                ItemTwoActivity South_China = new ItemTwoActivity("South_China");
                list.add(Black);
                list.add(Baltic);
                list.add(Red);
                list.add(Azov);
                list.add(Laptev);
                list.add(Barentc);
                list.add(Japanese);
                list.add(South_China);
                break;

            case "Lake":
                ItemTwoActivity Baikal = new ItemTwoActivity("Baikal");
                ItemTwoActivity Chelan = new ItemTwoActivity("Chelan");
                ItemTwoActivity Victoria = new ItemTwoActivity("Victoria");
                ItemTwoActivity Titicaca  = new ItemTwoActivity("Titicaca");
                list.add(Baikal);
                list.add(Chelan);
                list.add(Victoria);
                list.add(Titicaca);
                break;

            case "River":
                ItemTwoActivity Dnieper = new ItemTwoActivity("Dnieper");
                ItemTwoActivity Dniester = new ItemTwoActivity("Dniester");
                ItemTwoActivity Southern_Bug = new ItemTwoActivity("Southern_Bug");
                ItemTwoActivity Turunchuk  = new ItemTwoActivity("Turunchuk");
                ItemTwoActivity Yenisei = new ItemTwoActivity("Yenisei");
                ItemTwoActivity Lena = new ItemTwoActivity("Lena");
                ItemTwoActivity Ob  = new ItemTwoActivity("Ob");
                list.add(Dnieper);
                list.add(Dniester);
                list.add(Southern_Bug);
                list.add(Turunchuk);
                list.add(Yenisei);
                list.add(Lena);
                list.add(Ob);
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