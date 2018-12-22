package com.company.imetlin.fishmarker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.adapters.AdapterGrid;
import com.company.imetlin.fishmarker.database.SQLiteHelper;
import com.company.imetlin.fishmarker.pojo.ModelClass;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*

 //DROP BASE
      dbHelper = new SQLiteHelper(this);
      SQLiteDatabase database = dbHelper.getWritableDatabase();
      database.delete(SQLiteHelper.DB_TABLE_NAME,null,null);


//*   this.deleteDatabase("marker_db.db");


*/




        List<ModelClass> image_details = getListData();

        final GridView gridView = (GridView) findViewById(R.id.gridView);


        gridView.setAdapter(new AdapterGrid(this, image_details));

        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                ModelClass _modelClass = (ModelClass) o;
                //Toast.makeText(MainActivity.this, "Selected :"
                    //    + " " + ModelClass.getName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), WaterActivity.class);
                intent.putExtra("name", _modelClass.getName());
                startActivity(intent);
            }
        });

    }

    private  List<ModelClass> getListData() {

        List<ModelClass> list = new ArrayList<ModelClass>();
        ModelClass ocean = new ModelClass("Ocean", "ocean");
        ModelClass lake = new ModelClass("Lake", "ozero");
        ModelClass river = new ModelClass("River", "river");
        ModelClass sea = new ModelClass("Sea", "sea");

        ModelClass estuary = new ModelClass("Estuary", "liman");
        ModelClass pond = new ModelClass("Pond", "prud");



        list.add(ocean);
        list.add(lake);
        list.add(river);
        list.add(sea);

        list.add(estuary);
        list.add(pond);


        return list;

    }

}
