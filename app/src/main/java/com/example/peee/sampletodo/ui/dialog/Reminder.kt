package com.example.peee.sampletodo.ui.dialog

import android.content.Context
import com.example.peee.sampletodo.R
import java.util.concurrent.TimeUnit

/**
 * An enum that represents how early the app should reminds user against due date.
 */
enum class Reminder(val millis: Long, val resId: Int) {
    ON_TIME(0L, R.string.dialog_todo_item_reminder_on_time),
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
        /**
         * Generates [Reminder] that matches given string.
         *
         * @param context context to get string resource
         * @param string string to match
         * @return reminder matched, or [ON_TIME] if nothing matched
         */
        fun stringOf(context: Context, string: String): Reminder =
                values().find { context.getString(it.resId) == string } ?: ON_TIME

        /**
         * Generates [Reminder] that matches given time in milliseconds.
         *
         * @param millis time in milliseconds to match
         * @return reminder matched, or [ON_TIME] if nothing matched
         */
        fun millisOf(millis: Long): Reminder =
                values().find { it.millis == millis } ?: ON_TIME
    }
}
