package com.apps.a7pl4y3r.yourweek.independent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.databases.AlarmDb
import com.apps.a7pl4y3r.yourweek.helpers.DatePickerFragment
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment
import kotlinx.android.synthetic.main.activity_add_alarm.*
import kotlinx.android.synthetic.main.activity_create_alarm2.*
import java.util.*
import kotlin.math.min


class AddAlarm : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private lateinit var calendar: Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarm)
        calendar = Calendar.getInstance()

        btDate.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "AlarmDate")
        }

        btTime.setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "AlarmTime")
        }

        btCancel.setOnClickListener { finish() }

        btSaveAlarm.setOnClickListener {

            if (etAddAlarm.text.isNotEmpty()) {

                AlarmDb(this).insertAlarm(
                    Alarm(etAddAlarm.text.toString(), calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.get(Calendar.MONTH).toString()))

            } else {

                toastMessage(this, "You need to provide the alarm a name!", false)

            }

        }

    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, month)

        tvDate.text = "$dayOfMonth/${getMonthNameById(month)}/$year"

    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        tvTime.text = "$hourOfDay:$minute"

    }

}
