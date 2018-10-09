package com.company.imetlin.fishmarker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.adapters.AdapterGrid;
import com.company.imetlin.fishmarker.pojo.ItemFirstActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<ItemFirstActivity> image_details = getListData();

        final GridView gridView = (GridView) findViewById(R.id.gridView);


        gridView.setAdapter(new AdapterGrid(this, image_details));

        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                ItemFirstActivity itemFirstActivity = (ItemFirstActivity) o;
                Toast.makeText(MainActivity.this, "Selected :"
                        + " " + itemFirstActivity.getName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), WaterActivity.class);
                intent.putExtra("name", itemFirstActivity.getName());
                startActivity(intent);
            }
        });

    }

    private  List<ItemFirstActivity> getListData() {

        List<ItemFirstActivity> list = new ArrayList<ItemFirstActivity>();
        ItemFirstActivity ocean = new ItemFirstActivity("Ocean", "ocean");
        ItemFirstActivity lake = new ItemFirstActivity("Lake", "ozero");
        ItemFirstActivity river = new ItemFirstActivity("River", "river");
        ItemFirstActivity sea = new ItemFirstActivity("Sea", "sea");

        list.add(ocean);
        list.add(lake);
        list.add(river);
        list.add(sea);


        return list;
    }

}
