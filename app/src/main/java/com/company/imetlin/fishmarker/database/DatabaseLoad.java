package com.company.imetlin.fishmarker.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DatabaseLoad  {

    private SQLiteHelper sqLiteHelper;
    private final Context context;
    private ModelClass modelClass;
    public GoogleMap googlemap;



    public DatabaseLoad(Context context){
        this.context = context;
    }




    public void LoaderData(GoogleMap googlemap){

        List<ModelClass> markerlist = new ArrayList<ModelClass>();


        sqLiteHelper = new SQLiteHelper(context);

        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();


        Cursor cursor = database.query(SQLiteHelper.DB_TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){

            double longitudeIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_LONGITUDE);
            double latitudeIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_LATITUDE);
            String dateIndex = String.valueOf(cursor.getColumnIndex(SQLiteHelper.DB_COL_DATE));

            do {
                Log.d("mlog"," long = " + cursor.getDouble((int) longitudeIndex) +
                        " lat = " + cursor.getDouble((int) latitudeIndex) +
                        " date = " + cursor.getString(Integer.parseInt(dateIndex)));



                googlemap.addMarker(new MarkerOptions()
                        .position(new LatLng(cursor.getDouble((int) longitudeIndex),cursor.getDouble((int) latitudeIndex)))
                        .title(cursor.getString(Integer.parseInt(dateIndex))));

            }while (cursor.moveToNext());
        }else{
            Toast.makeText(context, "Base EMPTY", Toast.LENGTH_LONG).show();
        }


    }

}


