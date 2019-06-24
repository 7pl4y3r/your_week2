package com.apps.a7pl4y3r.yourweek.helpers

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

import java.util.*


class TimePickerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener?, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
    }

}
