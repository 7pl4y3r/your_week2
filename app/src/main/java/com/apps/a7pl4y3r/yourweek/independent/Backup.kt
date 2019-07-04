package com.apps.a7pl4y3r.yourweek.independent


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Day
import com.apps.a7pl4y3r.yourweek.databases.Daydb
import com.apps.a7pl4y3r.yourweek.databases.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_backup.*


class Backup : AppCompatActivity() {


    private lateinit var fireDb: FirebaseFirestore
    private val taskCollectionName = "Tasks"

    private val KEY_DAY = "Day"
    private val KEY_START_HOUR = "StartHour"
    private val KEY_START_MINUTE = "StartMinute"
    private val KEY_END_HOUR = "EndHour"
    private val KEY_END_MINUTE = "EndMinute"
    private val KEY_NAME = "Name"


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

    }


    private fun getDayTasks(dayName: String): ArrayList<Task> {

        val list = ArrayList<Task>()
        val res = Daydb(this, dayName).getData()

        if (res.count == 0) {
            list.add(Task("", "", "", "", ""))
            return list
        }

        res.moveToFirst()
        do {

            list.add(Task(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)))

        } while (res.moveToNext())

        return list
    }


    private fun uploadDay(dayName: String, list: ArrayList<Task>) {

        if (list.size > 0) {

            val map = HashMap<String, ArrayList<Task>>()
            map[KEY_DAY] = list

            fireDb.collection(taskCollectionName).document(dayName).set(map)
                .addOnSuccessListener {
                    toastMessage(this, "Tasks uploaded!", false)
                }
                .addOnFailureListener {
                    toastMessage(this, "Fail!", false)
                }

        }
    }

}
