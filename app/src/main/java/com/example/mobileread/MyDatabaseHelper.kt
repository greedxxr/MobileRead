package com.example.mobileread

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper (val context: Context?,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version){
    private val createUser = "create table User (" +
            "account text primary key," +
            "password text," +
            "name text," +
            "image blob)"
    private val createBook ="create table Book(" +
            "book_name text primary key," +
            "writer text," +
            "introduction text," +
            "img_src text," +
            "book_src text)"
    private val createUserwithBook="create table UwithB(" +
            "book_name text," +
            "account text)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUser)
        db.execSQL(createBook)
        db.execSQL(createUserwithBook)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldversionm: Int, newversion: Int) {
        if(oldversionm<=1){
            db.execSQL(createBook)
            db.execSQL(createUserwithBook)
        }
    }

}