package com.example.foodtrackingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.foodtrackingapp.helperclasses.Date;

import java.sql.PreparedStatement;

public class FoodTrackingAppDbHelper extends SQLiteOpenHelper {

    //vars
    private static final String LOG_TAG="FOODTRACKINGAPP";

    /* Inner class - definition aller tabelleninhalte */
    public static class FoodTrackingAppEntry implements BaseColumns {
        public static final String TABLE_PRODUKTLISTE = "Produktliste";
        private static final String TABLE_PRODUKTE_PRO_TAG= "Produkte_Pro_Tag";
        private static final String COL_NAME = "Produkt";
        private static final String COL_PROTEIN = "Protein";
        private static final String COL_KOHLENHYDRATE = "Kohlenhydrate";
        private static final String COL_FETT = "Fett";
        private static final String COL_KCAL = "Kcal";
        private static final String COL_MENGE = "Menge";
        private static final String COL_YEAR = "Year";
        private static final String COL_MONTH = "Month";
        private static final String COL_DAY = "Day";
        private static final String COL_HOUR = "Hour";
        private static final String COL_MINUTE ="Minute";

    }

    /* definition aller öäLL+-*/
    private static final String SQL_CREATE_TABLE_PRODUKTLISTE =
            "CREATE TABLE " + FoodTrackingAppEntry.TABLE_PRODUKTLISTE + " (" +
                    FoodTrackingAppEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodTrackingAppEntry.COL_NAME + " TEXT," +
                    FoodTrackingAppEntry.COL_PROTEIN + " INTEGER," +
                    FoodTrackingAppEntry.COL_KOHLENHYDRATE + " INTEGER," +
                    FoodTrackingAppEntry.COL_FETT + " INTEGER," +
                    FoodTrackingAppEntry.COL_KCAL + " INTEGER)";

    private static final String SQL_CREATE_TABLE_PRODUKTE_PRO_TAG =
            "CREATE TABLE " + FoodTrackingAppEntry.TABLE_PRODUKTE_PRO_TAG + " (" +
                    FoodTrackingAppEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodTrackingAppEntry.COL_MENGE + " INTEGER,"+
                    FoodTrackingAppEntry.COL_NAME + " TEXT," +
                    FoodTrackingAppEntry.COL_PROTEIN + " INTEGER," +
                    FoodTrackingAppEntry.COL_KOHLENHYDRATE + " INTEGER," +
                    FoodTrackingAppEntry.COL_FETT + " INTEGER," +
                    FoodTrackingAppEntry.COL_KCAL + " INTEGER," +
                    FoodTrackingAppEntry.COL_YEAR+ " INTEGER," +
                    FoodTrackingAppEntry.COL_MONTH + " INTEGER," +
                    FoodTrackingAppEntry.COL_DAY+ " INTEGER," +
                    FoodTrackingAppEntry.COL_HOUR + " INTEGER," +
                    FoodTrackingAppEntry.COL_MINUTE + " INTEGER)";

    private static final String SQL_DELETE_TABLE_PRODUKTLISTE =
            "DROP TABLE IF EXISTS " + FoodTrackingAppEntry.TABLE_PRODUKTLISTE;

    private static final String SQL_DELETE_TABLE_PRODUKTE_PRO_TAG =
            "DROP TABLE IF EXISTS " + FoodTrackingAppEntry.TABLE_PRODUKTE_PRO_TAG;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Produkte.db";

    public FoodTrackingAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PRODUKTLISTE);
        db.execSQL(SQL_CREATE_TABLE_PRODUKTE_PRO_TAG);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_PRODUKTLISTE);
        db.execSQL(SQL_DELETE_TABLE_PRODUKTE_PRO_TAG);
        onCreate(db);
    }

    private static int getExakteMenge(int anteilZuBerechnen, int inputMenge){
        return anteilZuBerechnen*inputMenge/100;
    }


    /*METHODS FOR PRODUCT PER DAY     */
    public static void addPruduktToPrudukteProTag(String name, int protein, int kohlenhydrate, int fett, int kcal, int inputMenge, Context context){

        FoodTrackingAppDbHelper dbHelper = new FoodTrackingAppDbHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FoodTrackingAppEntry.COL_NAME, name );
        values.put(FoodTrackingAppEntry.COL_MENGE, inputMenge);
        values.put(FoodTrackingAppEntry.COL_PROTEIN, getExakteMenge(protein, inputMenge));
        values.put(FoodTrackingAppEntry.COL_KOHLENHYDRATE, getExakteMenge(kohlenhydrate, inputMenge) );
        values.put(FoodTrackingAppEntry.COL_FETT, getExakteMenge(fett, inputMenge));
        values.put(FoodTrackingAppEntry.COL_KCAL, getExakteMenge(kcal, inputMenge));
        values.put(FoodTrackingAppEntry.COL_YEAR, Date.getCurrentYear());
        values.put(FoodTrackingAppEntry.COL_MONTH, Date.getCurrentMonth());
        values.put(FoodTrackingAppEntry.COL_DAY, Date.getCurrentDay());
        values.put(FoodTrackingAppEntry.COL_HOUR, Date.getCurrentHour());
        values.put(FoodTrackingAppEntry.COL_MINUTE, Date.getCurrentMinute());


// Insert the new row, returning the primary key value of the new row
       db.insert(FoodTrackingAppEntry.TABLE_PRODUKTE_PRO_TAG, null, values);

        Log.i(LOG_TAG, values+"");
    }

    public Cursor getAllProdukteFromProdukte_Pro_Tag(){

        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FoodTrackingAppEntry.COL_NAME,
                FoodTrackingAppEntry.COL_PROTEIN,
                FoodTrackingAppEntry.COL_KOHLENHYDRATE,
                FoodTrackingAppEntry.COL_FETT,
                FoodTrackingAppEntry.COL_KCAL,
                FoodTrackingAppEntry.COL_YEAR,
                FoodTrackingAppEntry.COL_MONTH,
                FoodTrackingAppEntry.COL_DAY,
                FoodTrackingAppEntry.COL_MINUTE
        };

/* Filter results WHERE "title" = 'My Title'
        String selection = FoodTrackingAppEntry.COL_NAME + " = ?";
        String[] selectionArgs = { "Quark" };
*/

        String selection = FoodTrackingAppEntry.COL_NAME + " = ?";
        String[] selectionArgs = {};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FoodTrackingAppEntry.COL_NAME + " DESC";
/*
        Cursor cursor = db.query(
                FoodTrackingAppEntry.TABLE_PRODUKTLISTE,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
               selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

     List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FoodTrackingAppEntry._ID));

            itemIds.add(itemId);
        }
*/
        String query = "SELECT *  FROM "+FoodTrackingAppEntry.TABLE_PRODUKTE_PRO_TAG+" WHERE "+FoodTrackingAppEntry.COL_YEAR+" = "+Date.getCurrentYear();

        Cursor data = db.rawQuery(query, null);

        return data;
    }

    /*METHODS FOR PRODUKTLISTE */
    public static void addPruduktToPruduktliste(String name, int protein, int kohlenhydrate, int fett, int kcal,  Context context){

        FoodTrackingAppDbHelper dbHelper = new FoodTrackingAppDbHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FoodTrackingAppEntry.COL_NAME, name );
        values.put(FoodTrackingAppEntry.COL_PROTEIN, protein );
        values.put(FoodTrackingAppEntry.COL_KOHLENHYDRATE, kohlenhydrate );
        values.put(FoodTrackingAppEntry.COL_FETT, fett );
        values.put(FoodTrackingAppEntry.COL_KCAL, kcal);

// Insert the new row, returning the primary key value of the new row
        db.insert(FoodTrackingAppEntry.TABLE_PRODUKTLISTE, null, values);

        Log.i(LOG_TAG, values+"");
    }

    public Cursor getAllProdukteFromProduktlise(String query){

        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FoodTrackingAppEntry.COL_NAME,
                FoodTrackingAppEntry.COL_PROTEIN,
                FoodTrackingAppEntry.COL_KOHLENHYDRATE,
                FoodTrackingAppEntry.COL_FETT,
                FoodTrackingAppEntry.COL_KCAL
        };

/* Filter results WHERE "title" = 'My Title'
        String selection = FoodTrackingAppEntry.COL_NAME + " = ?";
        String[] selectionArgs = { "Quark" };
*/

        String selection = FoodTrackingAppEntry.COL_NAME + " = ?";
        String[] selectionArgs = {};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FoodTrackingAppEntry.COL_NAME + " DESC";
/*
        Cursor cursor = db.query(
                FoodTrackingAppEntry.TABLE_PRODUKTLISTE,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
               selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

     List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FoodTrackingAppEntry._ID));

            itemIds.add(itemId);
        }
*/
       //String query = "SELECT * FROM " + FoodTrackingAppEntry.TABLE_PRODUKTLISTE;
        Cursor data = db.rawQuery(query, null);

        return data;
    }
}
