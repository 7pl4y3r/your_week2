package com.apps.a7pl4y3r.yourweek.independent


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Day
import com.apps.a7pl4y3r.yourweek.databases.Daydb
import com.apps.a7pl4y3r.yourweek.databases.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_backup.*
import kotlin.collections.ArrayList


class Backup : AppCompatActivity() {


    private lateinit var fireDb: FirebaseFirestore
    private val taskCollectionName = "Tasks"

    private val KEY_DAY = "Day"
    private val TAG = "Backup.kt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_backup)
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

            fireDb.collection(taskCollectionName).get()
                .addOnSuccessListener {

                    for (documentSnapShot in it) {

                        val day = documentSnapShot.toObject(Day::class.java)
                        Log.d(TAG, "${day.dayName} -> {\n")
                        for (element in day.tasks)
                            Log.d(TAG, "${element.task} -> ${element.startHour}:${element.startMinute} --- ${element.endHour}:${element.endMinute}\n")

                        Log.d(TAG, "}\n")

                    }

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

}
