package com.apps.a7pl4y3r.yourweek.independent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.databases.AlarmDb
import com.apps.a7pl4y3r.yourweek.helpers.DatePickerFragment
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment

import kotlinx.android.synthetic.main.activity_add_alarm.*

import java.util.Calendar


class EditAlarm : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_add_alarm)
        setCalendar()
        setUiData()
        setSupportActionBar(toolbarAddAlarm)
        toolbarAddAlarm.title = "Update alarm"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val roundBackground = getButtonDrawable(this)
            btDate.background = roundBackground
            btTime.background = roundBackground
            btCancel.background = roundBackground
            btSaveAlarm.background = roundBackground

        }

        btDate.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "DatePicker")
        }

        btTime.setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "TimePicker")
        }

        btCancel.setOnClickListener {

            cancelAlarm(this, getIdByName(intent.getStringExtra(nameExtra)))
            finish()

        }

        btSaveAlarm.setOnClickListener {

            val id = getIdByName(intent.getStringExtra(nameExtra))
            if (etAddAlarm.text.isNotEmpty())
                AlarmDb(this).updateAlarm(
                    id.toString(),
                    Alarm(etAddAlarm.text.toString(), calendar.get(Calendar.DAY_OF_MONTH).toString(), calendar.get(Calendar.MONTH).toString(),
                        calendar.get(Calendar.YEAR).toString(), calendar.get(Calendar.HOUR_OF_DAY).toString(), calendar.get(Calendar.MINUTE).toString()))

            cancelAlarm(this, id)
            startAlarm(this, id, etAddAlarm.text.toString(), calendar)
            getSharedPreferences(setAlarmAdded, Context.MODE_PRIVATE).edit().putBoolean(valSetAlarmAdded, true).apply()
            finish()

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.optionDelete -> {

                val id = getIdByName(intent.getStringExtra(nameExtra))
                cancelAlarm(this, id)
                AlarmDb(this).deleteAlarm(id.toString())
                getSharedPreferences(setAlarmAdded, Context.MODE_PRIVATE).edit().putBoolean(valSetAlarmAdded, true).apply()
                finish()

            }

        }

        return true
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


    private fun setCalendar() {

        calendar.set(Calendar.YEAR, intent.getStringExtra(yearExtra).toInt())
        calendar.set(Calendar.MONTH, intent.getStringExtra(monthExtra).toInt())
        calendar.set(Calendar.DAY_OF_MONTH, intent.getStringExtra(dayExtra).toInt())
        calendar.set(Calendar.HOUR_OF_DAY, intent.getStringExtra(hourExtra).toInt())
        calendar.set(Calendar.MINUTE, intent.getStringExtra(minuteExtra).toInt())
        calendar.set(Calendar.SECOND, 0)

    }


    private fun setUiData() {

        etAddAlarm.setText(intent.getStringExtra(nameExtra))
        tvDate.text = ("${calendar.get(Calendar.DAY_OF_MONTH)} ${getMonthNameById(calendar.get(Calendar.MONTH))} ${calendar.get(Calendar.YEAR)}")
        tvTime.text = ("${calendar.get(Calendar.HOUR_OF_DAY)} ${calendar.get(Calendar.MINUTE)}")

    }


    private fun getIdByName(name: String): Int {

        val res = AlarmDb(this).getAlarms()

        if (res.count == 0)
            return -1

        res.moveToFirst()
        do {

            if (res.getString(1) == name)
                return res.getInt(0)

        } while (res.moveToNext())

        return -1
    }

}