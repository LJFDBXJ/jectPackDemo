package com.example.jetpacktest.ext

import android.content.Context
import android.widget.Toast

var toast: Toast? = null
fun Context.toast(stringRes: String) {
    if (toast == null)
        toast = Toast.makeText(this, stringRes, Toast.LENGTH_SHORT)
    toast?.setText(stringRes)
    toast?.show()
    toast?.cancel()
}
