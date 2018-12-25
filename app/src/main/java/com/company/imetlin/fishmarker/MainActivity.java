package com.company.imetlin.fishmarker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    public Menu menu;

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
        ModelClass sea = new ModelClass("Sea", "sea");
        ModelClass river = new ModelClass("River", "river");
        ModelClass lake = new ModelClass("Lake", "ozero");

        ////zaliv
        ModelClass Gulf = new ModelClass("Gulf", "zaliv");


        ModelClass Another = new ModelClass("Another", "reservior");





        list.add(ocean);
        list.add(sea);
        list.add(river);
        list.add(lake);

        list.add(Gulf);
        list.add(Another);



        return list;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.information:
                //function information
                InformationWindow();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void InformationWindow(){


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Developer information")
                .setMessage("Developer: i.metlin" + "\n" +
                        "                    Ukraine" + "\n" +
                        "                    Odessa"+ "\n" +
                        "                    metlin.igor@gmail.com" + "\n" +
                        "                    Metlin Igor")
                .setIcon(R.drawable.information)
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
