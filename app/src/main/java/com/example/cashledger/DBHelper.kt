package com.example.cashledger

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Bookdata.db", null, 1) {
    override fun onCreate(DB: SQLiteDatabase) {
        DB.execSQL("CREATE TABLE Bookdetails(name TEXT PRIMARY KEY, amount TEXT)")
    }

    override fun onUpgrade(DB: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        DB.execSQL("DROP TABLE IF EXISTS Bookdetails")
    }

    fun insertUserData(name: String, amount: String): Boolean {
        val DB = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("amount", amount)
        }
        val result = DB.insert("Bookdetails", null, contentValues)
        return result != -1L
    }

    fun getData(): Cursor {
        val DB = this.writableDatabase
        return DB.rawQuery("SELECT * FROM Bookdetails", null)
    }
}
