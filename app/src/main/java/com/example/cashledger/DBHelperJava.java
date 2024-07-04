package com.example.cashledger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelperJava extends SQLiteOpenHelper {
    public DBHelperJava(Context context) {
        super(context, "Bookdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Bookdetails(name TEXT primary key, amount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Bookdetails");
    }

    public Boolean insertUserData(String name, String amount){
        SQLiteDatabase DB = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("amount", amount);
                long result = DB.insert("Bookdetails", null, contentValues);
                if(result == -1){
                    return false;
                } else {
                    return true;
                }
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from BookDetails", null);
        return cursor;
    }
}
