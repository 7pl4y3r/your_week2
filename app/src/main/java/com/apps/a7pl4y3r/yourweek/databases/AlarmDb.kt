package com.apps.a7pl4y3r.yourweek.databases

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


private const val databaseName = "alarmsdb"
private const val tableName = "alarmsTable"
private const val col0 = "ID"
private const val col1 = "Name"
private const val col2 = "DateDay"
private const val col3 = "DateMonth"
private const val col4 = "DateYear"
private const val col5 = "DateHour"
private const val col6 = "DateMinute"


class AlarmDb(context: Context) : SQLiteOpenHelper(context, databaseName, null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tableName ($col0 INTEGER PRIMARY KEY AUTOINCREMENT,$col1 TEXT,$col2 TEXT,$col3 TEXT," +
                "$col4 TEXT,$col5 TEXT,$col6 TEXT)")
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }


    fun insertAlarm(alarm: Alarm): Boolean {

        val cv = ContentValues()
        cv.put(col1, alarm.name)
        cv.put(col2, alarm.day)
        cv.put(col3, alarm.month)
        cv.put(col4, alarm.year)
        cv.put(col5, alarm.hour)
        cv.put(col6, alarm.minute)

        return this.writableDatabase.insert(tableName, null, cv) != -1L
    }


    fun getAlarms(): Cursor = this.writableDatabase.rawQuery("select * from $tableName", null)


    fun updateAlarm(id: String, alarm: Alarm): Boolean {

        val cv = ContentValues()
        cv.put(col1, alarm.name)
        cv.put(col2, alarm.day)
        cv.put(col3, alarm.month)
        cv.put(col4, alarm.year)
        cv.put(col5, alarm.hour)
        cv.put(col6, alarm.minute)

        this.writableDatabase.update(tableName, cv, "ID = ?", arrayOf(id))

        return true
    }


    fun deleteAlarm(id: String): Int? = this.writableDatabase.delete(tableName, "ID = ?", arrayOf(id))

}
