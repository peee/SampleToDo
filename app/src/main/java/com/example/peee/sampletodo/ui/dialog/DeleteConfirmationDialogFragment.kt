package com.example.peee.sampletodo.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.db.ToDoEntity

/**
 * A dialog fragment that asks user to confirm deletion of
 * selected to-do item in the DB. User selection is back to
 * parent fragment through [Callback].
 */
class DeleteConfirmationDialogFragment : DialogFragment() {
    companion object {
        private const val EXTRA_TODO = "todo"

        fun createFrom(todo: ToDoEntity): DeleteConfirmationDialogFragment =
                DeleteConfirmationDialogFragment().apply {
                    arguments = Bundle().apply { putSerializable(EXTRA_TODO, todo) }
                }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val todo = arguments?.getSerializable(EXTRA_TODO) as? ToDoEntity
                ?: return super.onCreateDialog(savedInstanceState)

        return AlertDialog.Builder(activity)
                .setTitle(R.string.dialog_todo_delete_title)
                .setMessage(getString(R.string.dialog_todo_delete_message, todo.title))
                .setNegativeButton(R.string.dialog_delete) {
                    dialog, _ -> onConfirmed(todo); dialog.dismiss()
                }
                .setNeutralButton(R.string.dialog_cancel) {
                    dialog, _ -> dialog.dismiss()
                }
                .create()
    }

    private fun onConfirmed(todo: ToDoEntity) {
        val callback = parentFragment as? Callback ?: return
        callback.onDeleteConfirmed(todo)
    }

    interface Callback {
        /**
         * Called when user confirmed deletion.
         *
         * @param todo to-do item user selected for deletion
         */
        fun onDeleteConfirmed(todo: ToDoEntity)
    }
}