package com.example.shishir.blood.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shishir on 7/15/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bloodPlus.db";

    static final String TABLE_DONOR = "donorTable";
    static final String COL_ID = "_id";
    static final String COL_DONOR_NAME = "name";
    static final String COL_GENDER = "gender";
    static final String COL_BLOOD_GROUP = "bloodGroup";
    static final String COL_LOCATION = "location";
    static final String COL_BIRTHDATE = "birthday";
    static final String COL_LASTDONATE = "lastDonate";
    static final String COL_CONTACT_NUMBER = "contactNumber";

    String TABLE_QUERY = "CREATE TABLE " + TABLE_DONOR + " ( " + COL_ID + " INTEGER PRIMARY KEY, " + COL_DONOR_NAME + " TEXT, " +
            COL_GENDER + " TEXT, " + COL_BLOOD_GROUP + " TEXT, " + COL_LOCATION + " TEXT, " + COL_BIRTHDATE + " TEXT, " + COL_LASTDONATE +
            " TEXT, " + COL_CONTACT_NUMBER + " TEXT )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + TABLE_DONOR);
        onCreate(db);

    }
}
