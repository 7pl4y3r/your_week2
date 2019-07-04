package com.apps.a7pl4y3r.yourweek.independent

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Daydb
import com.apps.a7pl4y3r.yourweek.databases.Task
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment

import kotlinx.android.synthetic.main.activity_add.*


class Add : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private var timePickerID: Int? = 0
    private var strStartHour: String? = null
    private var strStartMinute: String? = null
    private var strEndHour: String? = null
    private var strEndMinute: String? = null
    private var wantsUpdate: Boolean = false

    private var pivDay = -1
    private var dayOfSet = 0
    private var maxDay = 6


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbarAdd)
        initData()
        setActionBar()

        //Opens the first timePickerDialog and set it's id to 1 for use in onTimeSet()
        tvChosenStartTime.setOnClickListener {
            timePickerID = 1
            TimePickerFragment().show(supportFragmentManager,"Start time")
        }

        //Opens the second timePickerDialog and set it's id to 2 for use in onTimeSet()
        tvChosenEndTime.setOnClickListener {
            timePickerID = 2
            TimePickerFragment().show(supportFragmentManager,"End time")
        }

        btExitAdd.setOnClickListener { finish() }

        btMin1Day.setOnClickListener {

            if (pivDay + dayOfSet > 0) {
                dayOfSet--
                setActionBar()

            } else toastMessage(this, getString(R.string.low_limit), true)
        }

        btPlus1Day.setOnClickListener {

            if (pivDay + dayOfSet < maxDay) {
                dayOfSet++
                setActionBar()

            } else toastMessage(this, getString(R.string.high_limit), true)
        }

        //Inserts data in the database on button click
        btCreate.setOnClickListener{

            if (dataIsValid()) {

                addTask()
                getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, true).apply()
                wantsUpdate = true

            } else Toast.makeText(this,getString(R.string.more_info_required),Toast.LENGTH_SHORT).show()
        }
    }


    private fun initData() {

        wantsUpdate = false
        pivDay = intent.getIntExtra(dayExtra, 0)
        maxDay = if (getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE)
                .getBoolean(valueSettNumOfDays, false)) 4 else 6

    }

    private fun setActionBar() {
        toolbarAdd.title = "Add task - ${dayId()}"
    }

    private fun addTask() {

        val db = Daydb(this, dayId())
        val task = Task(strStartHour, strStartMinute, strEndHour, strEndMinute, etTask.text.toString())

        if (db.insertData(task)) {
            Toast.makeText(this, getString(R.string.task_remembered), Toast.LENGTH_SHORT).show()

            tvChosenStartTime.setText(R.string.no_start_time)
            tvChosenEndTime.setText(R.string.no_end_time)
            etTask.text = null
            db.close()

        } else Toast.makeText(this, getString(R.string.task_error), Toast.LENGTH_SHORT).show()
    }

    private fun dataIsValid(): Boolean {

        val startTime = tvChosenStartTime.text.toString()
        val endTime = tvChosenEndTime.text.toString()

        if(etTask.text.isEmpty() || startTime == getString(R.string.no_start_time) || endTime == getString(R.string.no_end_time))
            return false

        return true
    }

    private fun dayId(): String = when (pivDay + dayOfSet) {

            0 -> getString(R.string.monday)
            1 -> getString(R.string.tuesday)
            2 -> getString(R.string.wednesday)
            3 -> getString(R.string.thursday)
            4 -> getString(R.string.friday)
            5 -> getString(R.string.saturday)
            6 -> getString(R.string.sunday)
            else -> "LOLLOL"

        }

    /*Returns the time chosen by the user in the timePickerDialog for both cases
     *in the desired format. Depends on the settings. The default is american.
     */
    private fun getTime(hour: Int, minute: Int, id: Int): String {

        val strHour: String
        val strMinute: String


        /*if (getSharedPreferences(settHourFormat, Context.MODE_PRIVATE).getInt(valueSettHourFormat, 1) == 1) {
            //American
            if(hour >= 12) {
                strHour = "${hour - 12}"
                strMinute = if(minute < 10) "0$minute PM" else "$minute PM"

            } else {

                strHour = "$hour"
                strMinute = if(minute < 10) "0$minute AM" else "$minute AM"
            }

        } else {*/
            //International
            strHour = if (hour < 10) "0$hour" else "$hour"
            strMinute = if (minute < 10) "0$minute" else "$minute"
        //}

        when(id){

            1 -> {
                strStartHour = strHour
                strStartMinute = strMinute
            }
            2 -> {
                strEndHour = strHour
                strEndMinute = strMinute
            }
        }
        return "$strHour:$strMinute"
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {

        when(timePickerID) {
            1 -> tvChosenStartTime.text = getTime(hour, minute, 1)
            2 -> tvChosenEndTime.text = getTime(hour,minute, 2)
        }
    }
}