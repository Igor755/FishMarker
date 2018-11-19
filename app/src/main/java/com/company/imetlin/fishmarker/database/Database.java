package com.company.imetlin.fishmarker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.company.imetlin.fishmarker.pojo.MarkerModel;

import java.util.List;

public class Database {

    private final Context ctx;

    private SQLiteHelper dbHelper;

    private SQLiteDatabase mDB;

    public Database(Context ctx) {
        this.ctx = ctx;
    }

    public void open() {
        dbHelper = new SQLiteHelper(ctx, Const.DB_NAME, null, Const.DB_VERSION);
        mDB = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
    public Cursor getAllData() {
        return mDB.query(Const.DB_TABLE_NAME, null, null, null, null, null, Const.DB_COL_ID_PRIMARY + " DESC");
    }

    public void clearData() {
        mDB.delete(Const.DB_TABLE_NAME, null, null);
    }

    private void addMarkerDate(MarkerModel item) {

        ContentValues cv = new ContentValues();
        cv.put(Const.DB_COL_LONGITUDE, item.getLongitude());
        cv.put(Const.DB_COL_LATITUDE, item.getLatitude());
        cv.put(Const.DB_COL_DATE, item.getDate());
        cv.put(Const.DB_COL_DEPTH, item.getDepth());
        cv.put(Const.DB_COL_AMOUNT, item.getAmountoffish());
        cv.put(Const.DB_COL_NOTE, item.getNote());
        mDB.insert(Const.DB_TABLE_NAME, null, cv);

    }
    public void addApiData(List<MarkerModel> markeritems) {
        if (markeritems.size() != 0) {
            for (int i = markeritems.size() - 1; i >= 0; i--) {
                addMarkerDate(markeritems.get(i));
            }
        }
    }

}
