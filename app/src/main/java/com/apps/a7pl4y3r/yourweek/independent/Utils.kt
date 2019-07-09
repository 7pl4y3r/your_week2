package com.apps.a7pl4y3r.yourweek.independent

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.helpers.AlertReceiver
import java.util.*


fun setAppTheme(context: Context) {

    when(context.getSharedPreferences(settTheme, Context.MODE_PRIVATE).getInt(valueSettTheme, 1)) {
            //SkyBlue
            1 -> context.setTheme(R.style.AppTheme)
            //OceanDarkBlueTheme
            2 -> context.setTheme(R.style.OceanDarkBlueTheme)
            //GrassGreenTheme
            3 -> context.setTheme(R.style.GrassGreenTheme)
            //MarsRedTheme
            4 -> context.setTheme(R.style.MarsRedTheme)
            //AmoledDarkTheme
            5 -> context.setTheme(R.style.AMOLEDDark)
            //ImperialPurple
            6 -> context.setTheme(R.style.ImperialPurple)
            //OrangeBrick
            7 -> context.setTheme(R.style.OrangeBrick)
    }
}


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun getButtonDrawable(context: Context): Drawable? = when (context.getSharedPreferences(settTheme, Context.MODE_PRIVATE).getInt(valueSettTheme, 1)) {

    1 -> context.getDrawable(R.drawable.round_button_blue)
    2 -> context.getDrawable(R.drawable.round_button_blue_dark)
    3 -> context.getDrawable(R.drawable.round_button_green)
    4 -> context.getDrawable(R.drawable.round_button_red)
    5 -> context.getDrawable(R.drawable.round_button_blue_dark)
    6 -> context.getDrawable(R.drawable.round_button_purple)
    7 -> context.getDrawable(R.drawable.round_button_orange)

    else -> context.getDrawable(R.drawable.round_button_blue)

}


fun getMonthNameById(id: Int): String = when (id) {

        0 -> "Jan"
        1 -> "Feb"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "Aug"
        8 -> "Sep"
        9 -> "Oct"
        10 -> "Nov"
        11 -> "Dec"

        else -> "LOL"

}


fun toastMessage(context: Context, message: String, isLong: Boolean) {
    Toast.makeText(context, message,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}


fun startAlarm(context: Context, id: Int, name: String, calendar: Calendar) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlertReceiver::class.java)

    intent.putExtra(nameExtra, name)
    intent.putExtra(yearExtra, calendar.get(Calendar.YEAR))
    intent.putExtra(monthExtra, calendar.get(Calendar.MONTH))
    intent.putExtra(dayExtra, calendar.get(Calendar.DAY_OF_MONTH))
    intent.putExtra(hourExtra, calendar.get(Calendar.HOUR_OF_DAY))
    intent.putExtra(minuteExtra, calendar.get(Calendar.MINUTE))

    val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)

    if (calendar.before(Calendar.getInstance()))
        calendar.add(Calendar.DATE, 1)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    else
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

}


fun cancelAlarm(context: Context, id: Int) {

    if (id != -1) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)

        alarmManager.cancel(pendingIntent)

    } else {

        toastMessage(context, "There is no such alarm!", false)

    }

}


fun getCalendarDateString(year: Int, month: Int, day: Int, hour: Int, minute: Int): String = ("$hour:$minute $day ${getMonthNameById(month)} $year")
