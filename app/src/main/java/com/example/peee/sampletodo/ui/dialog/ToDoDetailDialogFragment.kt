package com.example.peee.sampletodo.ui.dialog

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
import com.example.peee.sampletodo.ui.DateFormatter

/**
 * A dialog fragment that allows user to edit to-do details.
 * It generates [ToDoEntity] based on user input back to parent fragment
 * through [Callback] interface.
 */
class ToDoDetailDialogFragment : DialogFragment(),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    companion object {
        private const val EXTRA_TODO = "todo"

        /**
         * Creates the dialog with filling in given [ToDoEntity].
         *
         * @param todo to-do data to fill in on the dialog
         * @return dialog with the to-do data
         */
        fun createFrom(todo: ToDoEntity): ToDoDetailDialogFragment =
                ToDoDetailDialogFragment().apply {
                    arguments = Bundle().apply { putSerializable(EXTRA_TODO, todo) }
                }
    }

    private lateinit var dueDateSwitch: Switch
    private lateinit var date: TextView
    private lateinit var time: TextView
    private lateinit var dateSetter: Button
    private lateinit var timeSetter: Button
    private lateinit var reminderSwitch: Switch
    private lateinit var reminder: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.dialog_todo_detail, null)

        with (view) {
            dueDateSwitch = findViewById(R.id.dialog_switch_todo_item_due_date)
            date = findViewById(R.id.dialog_text_todo_item_due_date)
            time = findViewById(R.id.dialog_text_todo_item_due_date_time)
            dateSetter = findViewById(R.id.dialog_button_todo_item_due_date)
            timeSetter = findViewById(R.id.dialog_button_todo_item_due_date_time)
            reminderSwitch = findViewById(R.id.dialog_switch_todo_item_reminder)
            reminder = findViewById(R.id.dialog_spinner_todo_item_reminder)
        }

        refreshDueDateEnableState(dueDateSwitch.isChecked)
        dueDateSwitch.setOnCheckedChangeListener {
            _, isChecked -> refreshDueDateEnableState(isChecked)
        }
        dateSetter.setOnClickListener {
            DatePickerDialogFragment().show(childFragmentManager, "date_picker_due_date")
        }
        timeSetter.setOnClickListener {
            TimePickerDialogFragment().show(childFragmentManager, "time_picker_due_date")
        }

        val adapter = ArrayAdapter.createFromResource(activity,
                R.array.reminders, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        reminder.adapter = adapter

        refreshReminderEnableState(reminderSwitch.isChecked)
        reminderSwitch.setOnCheckedChangeListener {
            _, isChecked -> refreshReminderEnableState(isChecked)
        }

        setToDo(view)

        return AlertDialog.Builder(activity)
                .setTitle(R.string.dialog_todo_detail_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_ok) {
                    dialog, _ -> buildToDo(view); dialog.dismiss()
                }
                .setNeutralButton(R.string.dialog_cancel) {
                    dialog, _ -> dialog.dismiss()
                }
                .create()
    }

    private fun refreshDueDateEnableState(isChecked: Boolean) {
        date.isEnabled = isChecked
        time.isEnabled = isChecked
        dateSetter.isEnabled = isChecked
        timeSetter.isEnabled = isChecked

        if (isChecked) {
            date.text = DateFormatter.toDateString(System.currentTimeMillis())
            time.text = DateFormatter.toTimeString(System.currentTimeMillis())
            dateSetter.setOnClickListener {
                DatePickerDialogFragment().show(childFragmentManager, "date_picker_due_date")
            }
            timeSetter.setOnClickListener {
                TimePickerDialogFragment().show(childFragmentManager, "time_picker_due_date")
            }
        }
    }

    private fun refreshReminderEnableState(isChecked: Boolean) {
        reminder.isEnabled = isChecked
    }

    private fun setToDo(view: View) {
        val todo = arguments?.getSerializable(EXTRA_TODO) as? ToDoEntity ?: return

        with (view) {
            findViewById<EditText>(R.id.dialog_edit_todo_item_title).setText(todo.title)
            findViewById<EditText>(R.id.dialog_edit_todo_item_description).setText(todo.description)
            dueDateSwitch.isChecked = todo.dueDate > 0
            if (dueDateSwitch.isChecked) {
                date.text = DateFormatter.toDateString(todo.dueDate)
                time.text = DateFormatter.toTimeString(todo.dueDate)
            }

            reminderSwitch.isChecked = todo.reminder > 0
            val diff = Reminder.millisOf(todo.dueDate - todo.reminder)
            val index = resources.getStringArray(R.array.reminders)
                    .indexOfFirst { it == getString(diff.resId) }
            reminder.setSelection(index)
        }
    }

    private fun buildToDo(view: View) {
        val title = view.findViewById<EditText>(R.id.dialog_edit_todo_item_title).text.toString()
        val description = view.findViewById<EditText>(R.id.dialog_edit_todo_item_description).text.toString()
        val dueDate = getDueDate(view)

        val todo = ToDoEntity(title, description, dueDate, getReminder(view, dueDate))
        arguments?.let { it.getSerializable(EXTRA_TODO) as? ToDoEntity }?.also { todo.id = it.id }

        val parent = parentFragment as? Callback ?: return
        parent.onToDoDialogComplete(todo)
    }

    private fun getDueDate(view: View): Long {
        if (!view.findViewById<Switch>(R.id.dialog_switch_todo_item_due_date).isChecked) return 0

        val dueDate = view.findViewById<TextView>(R.id.dialog_text_todo_item_due_date).text.toString()
        val dueDateTime = view.findViewById<TextView>(R.id.dialog_text_todo_item_due_date_time).text.toString()
        return DateFormatter.toMillis("$dueDate $dueDateTime")
    }

    private fun getReminder(view: View, dueDate: Long): Long {
        if (!view.findViewById<Switch>(R.id.dialog_switch_todo_item_reminder).isChecked) return 0

        val reminderString = view.findViewById<Spinner>(R.id.dialog_spinner_todo_item_reminder)
                .selectedItem as String
        val reminder = Reminder.stringOf(activity, reminderString)
        val time = dueDate - reminder.millis
        if (time < System.currentTimeMillis()) return 0
        return time
    }

    interface Callback {
        /**
         * Called when user finishes editing to-do data
         *
         * @param todo to-do data user edited on the dialog
         */
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
