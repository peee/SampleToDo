package com.example.peee.sampletodo.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.db.ToDoEntity

class ToDoDetailDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.dialog_todo_detail, null)
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
        val reminder = view.findViewById<TextView>(R.id.dialog_text_todo_item_reminder).text.toString()

        val todo = ToDoEntity(title, description,
                DateFormatter.toMillis(dueDate), DateFormatter.toMillis(reminder))

        val parent = parentFragment as? Callback ?: return
        parent.onToDoDialogComplete(todo)
    }


    interface Callback {
        fun onToDoDialogComplete(todo: ToDoEntity)
    }
}
