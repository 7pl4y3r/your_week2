package com.apps.a7pl4y3r.yourweek.independent

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.databases.AlarmDb
import com.apps.a7pl4y3r.yourweek.helpers.AlertReceiver
import com.apps.a7pl4y3r.yourweek.helpers.DatePickerFragment
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment

import kotlinx.android.synthetic.main.activity_add_alarm.*

import java.util.*


class AddAlarm : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private var calendar: Calendar = Calendar.getInstance()


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

                AlarmDb(this).insertAlarm(
                    Alarm(etAddAlarm.text.toString(), calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.get(Calendar.MONTH).toString(),
                        calendar.get(Calendar.YEAR).toString(), calendar.get(Calendar.HOUR_OF_DAY).toString(), calendar.get(Calendar.MINUTE).toString()))


                startAlarm(calendar)
                setAlarmAddedBoolean()

                toastMessage(this, "Alarm created!", false)

            } else {

                toastMessage(this, "You need to provide an alarm name!", false)

            }

        }

    }


    private fun startAlarm(calendar: Calendar) {

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        printCalendar(calendar)

        if (calendar.before(Calendar.getInstance()))
            calendar.add(Calendar.DATE, 1)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }


    private fun setAlarmAddedBoolean() {

        getSharedPreferences(setAlarmAdded, Context.MODE_PRIVATE).edit().putBoolean(valSetAlarmAdded, true).apply()
        finish()

    }


    private fun printCalendar(calendar: Calendar) {

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        println("year: $year")
        println("month: $month")
        println("day: $day")
        println("hour: $hour")
        println("minute: $minute")
        println("milisthen: ${calendar.timeInMillis}")
        println("milisthen: ${Calendar.getInstance()}")

    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        tvDate.text = "$dayOfMonth/${getMonthNameById(month)}/$year"

        toastMessage(this, "year$year\nmonth$month\ndayofmonth$dayOfMonth", true)

    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        toastMessage(this, "hour$hourOfDay\nminute$minute", true)

        tvTime.text = "$hourOfDay:$minute"

    }

}
