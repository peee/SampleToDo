package com.example.peee.sampletodo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.peee.sampletodo.db.ToDoDatabase
import java.util.concurrent.Executors

/**
 * A broadcast receiver to receive [Intent.ACTION_BOOT_COMPLETED]
 * to restore all alarms that had been set before the reboot.
 */
class AlarmRestoringReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val action = intent?.action ?: return

        if (action == Intent.ACTION_BOOT_COMPLETED) {
            restoreAllAlarmsInDb(context)
        }
    }

    private fun restoreAllAlarmsInDb(context: Context) {
        Executors.newSingleThreadExecutor().submit {
            val todoList = ToDoDatabase.getInstance(context).todoDao().loadAllItems()
            todoList.forEach {
                if (it.reminder >= System.currentTimeMillis()) AlarmHelper.set(context, it)
            }
        }
    }
}
