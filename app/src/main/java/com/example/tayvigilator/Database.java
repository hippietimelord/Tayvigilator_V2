package com.example.tayvigilator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static String DBName = "TAYVIG.db";
    private static String TBName = "SlotsRec";
    private static String COLRole = "Role";
    private static String COLStart = "Start Time";
    private static String COLEnd = "End Time";
    private static String COLDate = "Date";
    private static String COLVenue = "Venue";

    public Database (Context context) {
        super(context, DBName, null, 1);
    }

    public void onCreate (SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TBName);
        onCreate(db);
    }
}
