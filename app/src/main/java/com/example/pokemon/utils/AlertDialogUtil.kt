package com.example.pokemon.utils

import android.app.AlertDialog
import android.content.Context

class AlertDialogUtil(val context: Context) {
    fun showAlertDialog(
        title: String,
        message: String,
        positiveButton: Pair<String, () -> Unit>,
        negativeButton: String
    ) {
        AlertDialog.Builder(context).apply {
            this.setTitle(title)
            this.setMessage(message)
            this.setCancelable(false)
            this.setPositiveButton(positiveButton.first) { dialog, which ->
                positiveButton.second()
            }
            this.setNegativeButton(negativeButton) { dialog, which ->
                dialog.dismiss()
            }
        }.create().show()
    }
}
