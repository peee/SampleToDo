package com.example.peee.sampletodo.ui.dialog

import android.content.Context
import com.example.peee.sampletodo.R
import java.util.concurrent.TimeUnit

enum class Reminder(val millis: Long, val resId: Int) {
    MIN5(TimeUnit.MINUTES.toMillis(5), R.string.dialog_todo_item_reminder_5min),
    MIN15(TimeUnit.MINUTES.toMillis(15), R.string.dialog_todo_item_reminder_15min),
    MIN30(TimeUnit.MINUTES.toMillis(30), R.string.dialog_todo_item_reminder_30min),
    HOUR1(TimeUnit.HOURS.toMillis(1), R.string.dialog_todo_item_reminder_1hour),
    HOUR2(TimeUnit.HOURS.toMillis(2), R.string.dialog_todo_item_reminder_2hour),
    HOUR4(TimeUnit.HOURS.toMillis(4), R.string.dialog_todo_item_reminder_4hour),
    HOUR8(TimeUnit.HOURS.toMillis(8), R.string.dialog_todo_item_reminder_8hour),
    HOUR12(TimeUnit.HOURS.toMillis(12), R.string.dialog_todo_item_reminder_12hour),
    DAY1(TimeUnit.DAYS.toMillis(1), R.string.dialog_todo_item_reminder_1day),
    WEEK1(TimeUnit.DAYS.toMillis(7), R.string.dialog_todo_item_reminder_1week);

    companion object {
        fun stringOf(context: Context, string: String): Reminder =
                values().find { context.getString(it.resId) == string } ?: MIN5

        fun millisOf(millis: Long): Reminder =
                values().find { it.millis == millis } ?: MIN5
    }
}
