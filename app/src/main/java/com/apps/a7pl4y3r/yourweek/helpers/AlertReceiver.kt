package com.apps.a7pl4y3r.yourweek.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apps.a7pl4y3r.yourweek.independent.*


class AlertReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.mManager.notify(1,
            notificationHelper.getTestNotification(
                intent!!.getStringExtra(nameExtra), getCalendarDateString(
                    intent.getIntExtra(yearExtra, 1970),
                    intent.getIntExtra(monthExtra, 0),
                    intent.getIntExtra(dayExtra, 1),
                    intent.getIntExtra(hourExtra, 0),
                    intent.getIntExtra(minuteExtra, 0))).build())

    }

}
