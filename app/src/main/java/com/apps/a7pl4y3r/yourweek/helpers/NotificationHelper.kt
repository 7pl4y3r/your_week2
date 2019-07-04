package com.apps.a7pl4y3r.yourweek.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat
import com.apps.a7pl4y3r.yourweek.R


class NotificationHelper(context: Context) : ContextWrapper(context) {


    private val channelId = "7357"
    private val channelName = "Tests"

    val mManager: NotificationManager


    init {

        mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            mManager.createNotificationChannel(NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH))

    }


    fun getTestNotification(title: String, subtitle: String): NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
        .setSmallIcon(R.drawable.round_button_green)
        .setContentTitle(title)
        .setContentText(subtitle)

}
