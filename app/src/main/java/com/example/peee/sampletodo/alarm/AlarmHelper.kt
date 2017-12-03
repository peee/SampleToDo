package com.example.peee.sampletodo.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.peee.sampletodo.db.ToDoEntity

/**
 * A helper object to set/cancel alarm for to-do reminder.
 */
object AlarmHelper {
    /**
     * Sets alarm for to-do reminder. Updates if already exists.
     *
     * @param context context to get [PendingIntent] and [AlarmManager]
     * @param todo to-do data to remind
     */
    fun set(context: Context, todo: ToDoEntity) {
        val pendingIntent = getPendingIntent(context, todo, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, todo.reminder, pendingIntent)
    }

    /**
     * Cancels alarm for to-do reminder if exists.
     *
     * @param context context to get [PendingIntent] and [AlarmManager]
     * @param todo to-do data to cancel reminder for
     */
    fun cancel(context: Context, todo: ToDoEntity) {
        val pendingIntent = getPendingIntent(context, todo, 0)
        pendingIntent.cancel()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    const val EXTRA_TODO = "alarm_todo"

    private fun getPendingIntent(context: Context, todo: ToDoEntity, flag: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
                .putExtra(EXTRA_TODO, todo.title)

        return PendingIntent.getBroadcast(context, todo.id.toInt(), intent, flag)
    }
}