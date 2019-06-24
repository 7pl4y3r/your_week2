package com.apps.a7pl4y3r.yourweek.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlertReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        val nb = notificationHelper.getTestNotification("Title", "Sub")
        notificationHelper.getManager().notify(1, nb.build())

    }

}