package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.apps.a7pl4y3r.yourweek.R


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
