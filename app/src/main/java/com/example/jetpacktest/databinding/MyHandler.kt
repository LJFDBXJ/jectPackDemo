package com.example.jetpacktest.databinding

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.databinding.ObservableField


class MyHandler {
    fun customClick(view: View) {
        Log.d("tag", "aaa")
    }

    fun customClick(view: AppCompatCheckBox, name: ObservableField<String>) {
        name.set(view.isChecked.toString())
    }
}