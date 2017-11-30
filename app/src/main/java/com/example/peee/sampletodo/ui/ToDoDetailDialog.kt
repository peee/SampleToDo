package com.example.peee.sampletodo.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.*
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.db.ToDoEntity
import com.example.peee.sampletodo.ui.dialog.DatePickerDialogFragment
import com.example.peee.sampletodo.ui.dialog.TimePickerDialogFragment

class ToDoDetailDialog : DialogFragment(),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.dialog_todo_detail, null)

        with (view) {
            findViewById<Button>(R.id.dialog_button_todo_item_due_date).setOnClickListener {
                DatePickerDialogFragment().show(childFragmentManager, "date_picker_due_date")
            }
            findViewById<Button>(R.id.dialog_button_todo_item_due_date_time).setOnClickListener {
                TimePickerDialogFragment().show(childFragmentManager, "time_picker_due_date")
            }
        }

        return AlertDialog.Builder(activity)
                .setTitle(R.string.dialog_todo_detail_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_todo_detail_button_positive) {
                    dialog, _ -> buildToDo(view); dialog.dismiss()
                }
                .setNegativeButton(R.string.dialog_todo_detail_button_negative) {
                    dialog, _ -> dialog.dismiss()
                }
                .create()
    }


    private fun buildToDo(view: View) {
        val title = view.findViewById<EditText>(R.id.dialog_edit_todo_item_title).text.toString()
        val description = view.findViewById<EditText>(R.id.dialog_edit_todo_item_description).text.toString()

        val dueDate = view.findViewById<TextView>(R.id.dialog_text_todo_item_due_date).text.toString()
        val dueDateTime = view.findViewById<TextView>(R.id.dialog_text_todo_item_due_date_time).text.toString()

        val reminder = view.findViewById<TextView>(R.id.dialog_text_todo_item_reminder).text.toString()

        val todo = ToDoEntity(title, description,
                DateFormatter.toMillis("$dueDate $dueDateTime"), DateFormatter.toMillis(reminder))

        val parent = parentFragment as? Callback ?: return
        parent.onToDoDialogComplete(todo)
    }


    interface Callback {
        fun onToDoDialogComplete(todo: ToDoEntity)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dialog?.findViewById<TextView>(R.id.dialog_text_todo_item_due_date)?.text =
                getString(R.string.dialog_todo_item_due_date_format, year, month + 1, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dialog?.findViewById<TextView>(R.id.dialog_text_todo_item_due_date_time)?.text =
                getString(R.string.dialog_todo_item_due_date_time_format, hourOfDay, minute)
    }
}
