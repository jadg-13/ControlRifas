package site.soymegh.controlrifas.models

import android.app.AlertDialog
import android.content.Context
import site.soymegh.controlrifas.MainActivity


class Dialog<T>(view: T) {

 val view = view
    fun showDialog(title:String, msn:String) {
        val alertDialogBuilder = AlertDialog.Builder(view as Context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(msn)
        alertDialogBuilder.setPositiveButton("ok") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}