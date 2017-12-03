package com.example.peee.sampletodo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peee.sampletodo.notification.ReminderNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return

        val title = intent.getStringExtra(AlarmHelper.EXTRA_TODO)
        ReminderNotification.post(context, title)
    }
}