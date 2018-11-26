package com.company.imetlin.fishmarker.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.company.imetlin.fishmarker.pojo.ModelClass;

public class DatabaseLoad {

    private SQLiteHelper sqLiteHelper;
    private Context context;
    private ModelClass modelClass;


    SQLiteDatabase database = sqLiteHelper.getWritableDatabase();



    public DatabaseLoad(SQLiteDatabase database) {
        this.database = database;
    }
    public void LoaderData(){



        Cursor cursor = database.query(SQLiteHelper.DB_TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
          //  int idIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_ID);
            int longitudeIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_LONGITUDE);
            int latitudeIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_LATITUDE);
            int dateIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_DATE);
   /*         int depthIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_DEPTH);
            int amountIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_AMOUNT);
            int noteIndex = cursor.getColumnIndex(SQLiteHelper.DB_COL_NOTE);*/


            do {
                //cursor.getInt()

            }while (cursor.moveToNext());
        }else{
            Toast.makeText(context, "Base EMPTY", Toast.LENGTH_LONG).show();
        }



    }

}


