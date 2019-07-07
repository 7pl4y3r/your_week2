package com.apps.a7pl4y3r.yourweek.independent


import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.*
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_backup.*
import kotlin.collections.ArrayList
import kotlin.math.round


class Backup : AppCompatActivity() {


    private lateinit var fireDb: FirebaseFirestore
    private val taskCollectionName = "Tasks"
    private val alarmCollectionName = "Alarms"

    private val TAG = "Backup.kt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_backup)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val roundBackground = getButtonDrawable(this)
            btBackupTasks.background = roundBackground
            btBackupAlarms.background = roundBackground
            btImportTasks.background = roundBackground
            btImportAlarms.background = roundBackground

        }

        FirebaseApp.initializeApp(this)
        fireDb = FirebaseFirestore.getInstance()

        btBackupTasks.setOnClickListener {

            uploadDay(getString(R.string.monday), getDayTasks(getString(R.string.monday)))
            uploadDay(getString(R.string.tuesday), getDayTasks(getString(R.string.tuesday)))
            uploadDay(getString(R.string.wednesday), getDayTasks(getString(R.string.wednesday)))
            uploadDay(getString(R.string.thursday), getDayTasks(getString(R.string.thursday)))
            uploadDay(getString(R.string.friday), getDayTasks(getString(R.string.friday)))

        }

        btBackupAlarms.setOnClickListener {

            val res = AlarmDb(this).getAlarms()
            if (res.count > 0) {

                res.moveToFirst()
                do {

                    uploadAlarm(Alarm(res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4), res.getString(5), res.getString(6)))

                } while (res.moveToNext())


            } else {
                toastMessage(this, "No alarms!", false)
            }


        }

        btImportTasks.setOnClickListener {

            fireDb.collection(taskCollectionName).get()
                .addOnSuccessListener {

                    for (documentSnapShot in it) {

                        val day = documentSnapShot.toObject(Day::class.java)
                        deleteDay(day.dayName)
                        downLoadDay(day.dayName, day.tasks)

                    }

                }

        }

        btImportAlarms.setOnClickListener {

            deleteAlarms()
            fireDb.collection(alarmCollectionName).get()
                .addOnSuccessListener {

                    for (query in it)
                        downloadAlarm(query.toObject(Alarm::class.java))

                }
        }

    }


    private fun getDayTasks(dayName: String): ArrayList<Task>? {

        val list: ArrayList<Task>?
        val res = Daydb(this, dayName).getData()

        if (res.count == 0)
            return null


        list = ArrayList()
        res.moveToFirst()
        do {

            list.add(Task(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)))

        } while (res.moveToNext())

        return list
    }


    private fun uploadDay(dayName: String, list: ArrayList<Task>?) {

            fireDb.collection(taskCollectionName).document(dayName).set(Day(dayName, list))
                .addOnSuccessListener {
                    toastMessage(this, "Tasks uploaded!", false)
                }
                .addOnFailureListener {
                    toastMessage(this, "Fail!", false)
                }
    }


    private fun uploadAlarm(alarm: Alarm) {

        fireDb.collection(alarmCollectionName).document(alarm.name).set(alarm)
            .addOnSuccessListener {
                toastMessage(this, "Alarm uploaded!", false)
            }
            .addOnFailureListener {
                toastMessage(this, it.toString(), false)
            }
    }


    private fun deleteDay(dayName: String) {

        val dayDb = Daydb(this, dayName)
        val res = dayDb.getData()

        if (res.count > 0) {

            res.moveToFirst()
            do {

                dayDb.deleteData(res.getString(0))

            } while (res.moveToNext())

        }

    }


    private fun downLoadDay(dayName: String, taskList: List<Task>) {

        val db = Daydb(this, dayName)
        for (element in taskList)
            db.insertData(element)

    }


    private fun deleteAlarms() {

        val alarmDb = AlarmDb(this)
        val res = alarmDb.getAlarms()

        if (res.count > 0) {

            res.moveToFirst()
            do {

                alarmDb.deleteAlarm(res.getString(0))

            } while (res.moveToNext())

        }

    }


    private fun downloadAlarm(alarm: Alarm) {
        AlarmDb(this).insertAlarm(alarm)
    }

}
