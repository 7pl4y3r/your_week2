package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.databases.AlarmDb
import com.apps.a7pl4y3r.yourweek.helpers.RvAlarms

import kotlinx.android.synthetic.main.activity_alarms.*


class Alarms : AppCompatActivity() {


    private val itemList = ArrayList<Alarm>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_alarms)
        setItemList()
        setRecyclerView()


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

        val res = AlarmDb(this).getAlarms()

        if (res.count == 0) {
            itemList.add(Alarm("No alarms!", "", "0", "", "", ""))
            return
        }


        res.moveToFirst()
        do {

            itemList.add(Alarm(res.getString(1), res.getString(2),
                res.getString(3), res.getString(4),
                res.getString(5), res.getString(6)))

        } while (res.moveToNext())

    }


    private fun setRecyclerView() {

        val adapter = RvAlarms(this, itemList)

        rvAlarms.setHasFixedSize(true)
        rvAlarms.layoutManager = LinearLayoutManager(this)
        rvAlarms.adapter = adapter

        if (itemList[0].name != "No alarms!" && itemList[0].day != "" && itemList[0].month != "0" && itemList[0].year != "" && itemList[0].hour != "" && itemList[0].minute != "") {

            adapter.setOnItemClickListener(object : RvAlarms.OnItemClickListener {

                override fun onItemClick(position: Int) {

                    val intent = Intent(this@Alarms, EditAlarm::class.java)
                    intent.putExtra(nameExtra, itemList[position].name)
                        .putExtra(yearExtra, itemList[position].year)
                        .putExtra(monthExtra, itemList[position].month)
                        .putExtra(dayExtra, itemList[position].day)
                        .putExtra(hourExtra, itemList[position].hour)
                        .putExtra(minuteExtra, itemList[position].minute)

                    startActivity(intent)

                }

            })

        }

    }

}
