package com.apps.a7pl4y3r.yourweek.independent

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
        getItemList()

        if (itemList != null) {

            val adapter = RvAlarms(this, itemList!!)
            rvAlarms.setHasFixedSize(true)
            rvAlarms.layoutManager = LinearLayoutManager(this)
            rvAlarms.adapter = adapter

        } else {

            toastMessage(this, "There are currently no alarms", true)

        }

        fabAddAlarm.setOnClickListener { startActivity(Intent(this, AddAlarm::class.java)) }

    }


    private fun getItemList() {

        val db = AlarmDb(this)
        val res = db.getAlarms()

        if (res.count == 0)
            return

        res.moveToFirst()
        do {

            itemList!!.add(Alarm(res.getString(1), res.getString(2),
                res.getString(3), res.getString(4),
                res.getString(5), res.getString(6)))

        } while (res.moveToNext())

    }

}
