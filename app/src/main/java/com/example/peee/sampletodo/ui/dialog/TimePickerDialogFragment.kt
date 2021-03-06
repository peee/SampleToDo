package com.example.peee.sampletodo.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

/**
 * A time picker dialog fragment. Parent fragment should
 * implement [TimePickerDialog.OnTimeSetListener] to receive time picked.
 */
class TimePickerDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = parentFragment as? TimePickerDialog.OnTimeSetListener
        val calendar = Calendar.getInstance()

        return TimePickerDialog(activity, listener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                true)
    }
}