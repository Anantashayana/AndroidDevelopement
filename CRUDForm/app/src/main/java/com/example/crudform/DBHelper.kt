package com.example.crudform

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Userdata.db", null, 1) {

    override fun onCreate(DB: SQLiteDatabase) {
        DB.execSQL("create Table Userdetails(name TEXT primary key, contact TEXT, dob TEXT)")
    }

    override fun onUpgrade(DB: SQLiteDatabase, i: Int, ii: Int) {
        DB.execSQL("drop Table if exists Userdetails")
    }

    fun insertuserdata(name: String, contact: String, dob: String): Boolean {
        val DB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("contact", contact)
        contentValues.put("dob", dob)
        val result = DB.insert("Userdetails", null, contentValues)
        return result != -1L
    }

    fun updateuserdata(name: String, contact: String, dob: String): Boolean {
        val DB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("contact", contact)
        contentValues.put("dob", dob)
        val cursor: Cursor = DB.rawQuery("Select * from Userdetails where name = ?", arrayOf(name))
        return if (cursor.count > 0) {
            val result = DB.update("Userdetails", contentValues, "name=?", arrayOf(name))
            result != -1
        } else {
            false
        }
    }

    fun deletedata(name: String): Boolean {
        val DB = this.writableDatabase
        val cursor: Cursor = DB.rawQuery("Select * from Userdetails where name = ?", arrayOf(name))
        return if (cursor.count > 0) {
            val result = DB.delete("Userdetails", "name=?", arrayOf(name))
            result != -1
        } else {
            false
        }
    }

    fun getdata(): Cursor {
        val DB = this.writableDatabase
        return DB.rawQuery("Select * from Userdetails", null)
    }
}
