package com.example.peee.sampletodo.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

class DatePickerDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = parentFragment as? DatePickerDialog.OnDateSetListener ?:
                return super.onCreateDialog(savedInstanceState)

        val calendar = Calendar.getInstance()

        return DatePickerDialog(activity, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
    }
}