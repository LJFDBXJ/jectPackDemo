package com.example.jetpacktest.databinding

import androidx.databinding.InverseMethod

object ConvertAdapter {

    @InverseMethod("toBoolean")
    @JvmStatic
    fun setChange(isCheck: Boolean): String {
        return isCheck.toString()
    }

    @JvmStatic
    fun toBoolean(isCheck: String):Boolean  {
        return isCheck.toBoolean()
    }

}