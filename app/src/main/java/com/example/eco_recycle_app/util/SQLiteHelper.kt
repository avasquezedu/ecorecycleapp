package com.example.eco_recycle_app.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "recycler.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS campaigns" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "start_date TEXT NOT NULL," +
                "end_date TEXT NOT NULL," +
                "type TEXT NOT NULL," +
                "status TEXT NOT NULL);"
        val sql2 = "CREATE TABLE IF NOT EXISTS users" +
                "(user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "full_name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL);"
        db.execSQL(sql)
        db.execSQL(sql2)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS campaigns")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }
}