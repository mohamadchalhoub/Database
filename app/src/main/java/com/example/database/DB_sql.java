package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.EditText;

import java.util.ArrayList;

public class DB_sql extends SQLiteOpenHelper {
    public static final String DBname = "data.db";
    public DB_sql(@Nullable Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE mobile(id Integer primary key AUTOINCREMENT, name Text, last_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS mobile");
        onCreate(db);
    }

    public boolean insertData(String name, String lname, String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV= new ContentValues();
        CV.put("name", name);
        CV.put("Last_name", lname);
        CV.put("id", ID);
        long result = db.insert("mobile",  null, CV);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList getRecords(){
        ArrayList arr = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs = db.rawQuery("select * from mobile", null);
        crs.moveToFirst();
            while (crs.isAfterLast() == false) {
                String V1 = crs.getString(0);
                String V2 = crs.getString(1);
                String V3 = crs.getString(2);
                arr.add(V1+":"+V2+" "+V3);
                crs.moveToNext();
            }
            db.close();
        return arr;
    }
    public  Integer deleteTable(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Integer rs = db.delete("mobile", "id=?", new String[]{id});
        db.close();
        return rs;
    }
    public boolean updateTable(String name, String Lname, String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("last_name", Lname);
        db.update("mobile", cv, "id=?",new String[]{ID});
        db.close();
        return true;
    }
}
