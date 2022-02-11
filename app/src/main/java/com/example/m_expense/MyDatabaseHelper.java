//Reference https://www.freakyjolly.com/android-sqlite-example-application-insert-update-delete-truncate-operations/
//Used university materials
package com.example.m_expense;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "M_Expense.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "trip";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_RISK = "risk";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String TABLE_NAME2 = "expenses";
    private static final String COLUMN_ID2 = COLUMN_ID;
    private static final String COLUMN_TYPE2 = "type";
    private static final String COLUMN_AMOUNT2 = "amount";
    private static final String COLUMN_TIME2 = "time";
    private static final String COLUMN_COMMENT2 = "comment";
    private static final String COLUMN_ID2_IMAGE_NAME = "image_name";
    private static final String COLUMN_ID2_IMAGE = "image";
    private static final String COLUMN_ID2_COLUMN_ID = "trip_id";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    //query of creating new table and execute by db.execSQL()
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_DESTINATION + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_RISK + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT);";
        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE2 + " TEXT, " +
                COLUMN_AMOUNT2 + " TEXT, " +
                COLUMN_TIME2 + " TEXT, " +
                COLUMN_COMMENT2 + " TEXT, " +
                COLUMN_ID2_IMAGE_NAME + " TEXT, " +
                COLUMN_ID2_IMAGE + " TEXT, " +
                COLUMN_ID2_COLUMN_ID + " TEXT NOT NULL);";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    //enter trip details
    void addTrip(String name,String destination,String date,String risk,String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_RISK, risk);
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1)
        {
            Log.d("msg: ","fail");
        }
        else
        {   Log.d("msg: ","pass");
        }
    }

    //enter expenses
    long addExpenses(String type, String amount, String time, String comment, String id1, String image_name, byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(image_name.isEmpty())
        {
            image_name="empty";
        }
        if(image.length==0)
        {
            image_name="0";
        }
        cv.put(COLUMN_TYPE2, type);
        cv.put(COLUMN_AMOUNT2, amount);
        cv.put(COLUMN_TIME2, time);
        cv.put(COLUMN_COMMENT2, comment);
        cv.put(COLUMN_ID2_COLUMN_ID, id1);
        cv.put(COLUMN_ID2_IMAGE_NAME,image_name);
        cv.put(COLUMN_ID2_IMAGE,image);
        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1)
        {
            Log.d("msg2: ","fail");
        }
        else
        {
            Log.d("msg2: ","pass");
            Log.d("msg2: "," "+id1);
            Log.d("msg2: "," "+type);
            Log.d("msg2: "," "+amount);
            Log.d("msg2: "," "+time);
            Log.d("msg2: "," "+comment);
        }
        return result;
    }

    //read all data of trip
    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //read all data of expenses which is not usefull here
    Cursor readAllData2()
    {
        String query = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //read only that expeses which are related to perticular trip
    //for ex: if I am curretly open meeting trip and get all expeses of thet trip, so user get only that
    //trip which are related to that trip by using id of meeting trip
    Cursor readTripData2(String id)
    {
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE "+ COLUMN_ID2_COLUMN_ID +" = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //read image from database but this feature is still not implemented
    Cursor readTripDataImage(String type)
    {
        String query = "SELECT id FROM " + TABLE_NAME2 + " WHERE "+ COLUMN_ID2_COLUMN_ID +" = '"+type+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //update trip data
    void updateData(String row_id, String name, String destination, String date, String risk, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_RISK, risk);
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.update(TABLE_NAME,cv,"id=?",new String[]{(row_id)});
        if(result == -1)
        {
            Log.d("msg: ","error in updation");
        }
        else
        {
            Log.d("msg: ","success in updatation");
        }
    }

    // delete whole trip buy its id
    void deleteOneRow(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"id=?", new String[]{row_id});
        if(result == -1)
        {
            Log.d("msg: ","error in deletation");
        }
        else
        {
            Log.d("msg: ","success in deletation");
        }
    }

    //delete whole table
    void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}










































