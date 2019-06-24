package com.apps.a7pl4y3r.yourweek.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlertReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        notificationHelper.mManager.notify(1, notificationHelper.getTestNotification("Title", "Sub").build())

    }

}
