package com.example.peee.sampletodo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peee.sampletodo.notification.ReminderNotification

/**
 * A broadcast receiver to handle alarm event by [AlarmHelper].
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return

        val title = intent.getStringExtra(AlarmHelper.EXTRA_TODO_TITLE)
        val id = intent.getLongExtra(AlarmHelper.EXTRA_TODO_ID, 0)
        ReminderNotification.post(context, title, id)
    }
}