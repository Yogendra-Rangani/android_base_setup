package com.example.android_base.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import com.example.android_base.R

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun Context.show(): Dialog {
    val dialog = Dialog(this)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val inflater = LayoutInflater.from(this)
    val view = inflater.inflate(R.layout.dialog_loader, null)
    dialog.setContentView(view)
    dialog.setCancelable(false)
    dialog.show()
    return dialog
}

fun Dialog.dismiss() {
    if (this.isShowing) {
        this.dismiss()
    }
}