package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.databases.AlarmDb
import com.apps.a7pl4y3r.yourweek.helpers.RvAlarms
import kotlinx.android.synthetic.main.activity_alarms.*


class Alarms : AppCompatActivity() {


    private var itemList: ArrayList<Alarm>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarms)
        setItemList()

        if (itemList != null) {

            val adapter = RvAlarms(this, itemList!!)
            rvAlarms.adapter = adapter
            rvAlarms.layoutManager = LinearLayoutManager(this)

            println("Size of list is ${itemList!!.size}")

        } else {

            toastMessage(this, "There are currently no alarms", true)

        }

        fabAddAlarm.setOnClickListener { startActivity(Intent(this, AddAlarm::class.java)) }

    }


    override fun onResume() {
        super.onResume()

        val pref = getSharedPreferences(setAlarmAdded, Context.MODE_PRIVATE)
        if (pref.getBoolean(valSetAlarmAdded, false)) {

            pref.edit().putBoolean(valSetAlarmAdded, false).apply()
            startActivity(Intent(this, Alarms::class.java))
            finish()

        }

    }

    private fun setItemList() {

        val db = AlarmDb(this)
        val res = db.getAlarms()

        if (res.count == 0)
            return

        itemList = ArrayList()
        res.moveToFirst()
        do {

            itemList!!.add(Alarm(res.getString(1), res.getString(2),
                res.getString(3), res.getString(4),
                res.getString(5), res.getString(6)))

        } while (res.moveToNext())

    }

}
