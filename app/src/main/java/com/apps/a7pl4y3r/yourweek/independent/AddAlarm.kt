package com.apps.a7pl4y3r.yourweek.independent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.databases.AlarmDb
import com.apps.a7pl4y3r.yourweek.helpers.DatePickerFragment
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment

import kotlinx.android.synthetic.main.activity_add_alarm.*

import java.util.Calendar


class AddAlarm : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private val calendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarm)

        btDate.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "AlarmDate")
        }


        btTime.setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "AlarmTime")
        }


        btCancel.setOnClickListener { finish() }


        btSaveAlarm.setOnClickListener {

            if (etAddAlarm.text.isNotEmpty()) {

                val db = AlarmDb(this)
                db.insertAlarm(
                    Alarm(etAddAlarm.text.toString(), calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.get(Calendar.MONTH).toString(),
                        calendar.get(Calendar.YEAR).toString(), calendar.get(Calendar.HOUR_OF_DAY).toString(), calendar.get(Calendar.MINUTE).toString()))

                val res = db.getAlarms()
                res.moveToLast()

                startAlarm(this, res.getString(0).toInt(), etAddAlarm.text.toString(), calendar)
                getSharedPreferences(setAlarmAdded, Context.MODE_PRIVATE).edit().putBoolean(valSetAlarmAdded, true).apply()
                finish()

                toastMessage(this, "Alarm created!", false)

            } else {

                toastMessage(this, "You need to provide an alarm name!", false)

            }

        }

    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        tvDate.text = "$dayOfMonth/${getMonthNameById(month)}/$year"

    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        tvTime.text = "$hourOfDay:$minute"

    }

}
