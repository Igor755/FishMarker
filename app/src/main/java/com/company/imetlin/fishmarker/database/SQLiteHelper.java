package com.company.imetlin.fishmarker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public final static int LOADER_ID = 0;



    public final static String DB_NAME = "marker_db";
    public final static String DB_TABLE_NAME = "marker_information";
    public final static String DB_COL_ID_PRIMARY = "_id";


    public final static String DB_COL_ID = "markerId";
    public final static String DB_COL_LONGITUDE= "longitude";
    public final static String DB_COL_LATITUDE = "latitude";
    public final static String DB_COL_DATE = "date";
    public final static String DB_COL_DEPTH = "depth";
    public final static String DB_COL_AMOUNT = "amountoffish";
    public final static String DB_COL_NOTE = "note";

    public final static int DB_VERSION = 1;


    public static final String DB_CREATE =
            "create table " + DB_TABLE_NAME + "("
                    + DB_COL_ID_PRIMARY + " integer primary key autoincrement,"
                    + DB_COL_ID + " integer,"
                    + DB_COL_LONGITUDE + " float,"
                    + DB_COL_LATITUDE + " float,"
                    + DB_COL_DATE + " text,"
                    + DB_COL_DEPTH + " float,"
                    + DB_COL_AMOUNT + " integer,"
                    + DB_COL_NOTE + " text," + " UNIQUE ("
                    + DB_COL_ID + " ) ON CONFLICT IGNORE" + " );";

    public static final String DB_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DB_TABLE_NAME;



    private SQLiteDatabase mDB;

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public SQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

    }
    public SQLiteHelper(Context context, String name,  int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DB_DELETE_ENTRIES);
        onCreate(db);
    }
}
