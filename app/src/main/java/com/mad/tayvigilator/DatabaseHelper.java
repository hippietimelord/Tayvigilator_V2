package com.mad.tayvigilator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "SLOT.db";
    private static String TABLE_NAME = "SLOTREC";
    private static String COL_1 = "ID";
    private static String COL_2 = "ROLE";
    private static String COL_3 = "START_TIME";
    private static String COL_4 = "END_TIME";
    private static String COL_5 = "DATE";
    private static String COL_6 = "VENUE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_RECORDS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT,"
                + COL_5 + " TEXT,"
                + COL_6 + " TEXT"
                + ")";
        db.execSQL(CREATE_RECORDS_TABLE);

        Log.i("CREATED","The table has been created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // To View Data which is in View Slots
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT DATE, ROLE, VENUE, START_TIME , END_TIME FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("DATE",cursor.getString(cursor.getColumnIndex(COL_5)));
            user.put("ROLE",cursor.getString(cursor.getColumnIndex(COL_2)));
            user.put("VENUE",cursor.getString(cursor.getColumnIndex(COL_6)));
            user.put("START_TIME",cursor.getString(cursor.getColumnIndex(COL_3)));
            user.put("END_TIME",cursor.getString(cursor.getColumnIndex(COL_4)));
            userList.add(user);
        }
        return  userList;
    }


    public Boolean insertData(String role, String start_time, String end_time, String date, String venue){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_2, role);
        contentValues.put(COL_3, start_time);
        contentValues.put(COL_4, end_time);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, venue);

        long result=db.insert(TABLE_NAME, null, contentValues);

        db.close();

        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    public boolean updateRecords(String id, String role, String start_time, String end_time, String date, String venue){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, role);
        contentValues.put(COL_3, start_time);
        contentValues.put(COL_4, end_time);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, venue);

        db.update(TABLE_NAME,contentValues, "ID = ?", new String [] {id});
        return true;
    }

    public int deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[] {id});

    }//we are deleting data on the basis of ID, this method returns an integer

    public int delete(String first_name){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"Name = ?", new String[] {first_name});

    }//we are deleting data on the basis of first_name
}

