package com.example.peee.sampletodo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.ui.MainActivity

/**
 * A helper object to handle notification for to-do reminder
 */
object ReminderNotification {
    /**
     * Posts notification for to-do reminder
     *
     * @param context context to get [NotificationManager], string resources, and notification objects
     */
    fun post(context: Context, message: String, id: Long) {
        val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context)
        } else {
            val channel = NotificationChannel(NotificationConsts.CHANNEL_REMINDER,
                    context.getString(R.string.notification_channel_reminder),
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            NotificationCompat.Builder(context, NotificationConsts.CHANNEL_REMINDER)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                context, id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = builder
                .setSmallIcon(R.drawable.notification_reminder)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(NotificationConsts.ID_REMINDER, notification)
    }
}