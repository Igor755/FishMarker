package com.company.imetlin.fishmarker.database;

public class Const {

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
                    + DB_COL_ID_PRIMARY + " integer primary key autoincrement, "
                    + DB_COL_ID + " integer, "
                    + DB_COL_LONGITUDE + " float, "
                    + DB_COL_LATITUDE + " float, "
                    + DB_COL_DATE + " text,"
                    + DB_COL_DEPTH + " float,"
                    + DB_COL_AMOUNT + " integer,"
                    + DB_COL_NOTE + " text," + " UNIQUE ( "
                    + DB_COL_ID + " ) ON CONFLICT IGNORE" + ");";

    public static final String DB_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DB_TABLE_NAME;
}


