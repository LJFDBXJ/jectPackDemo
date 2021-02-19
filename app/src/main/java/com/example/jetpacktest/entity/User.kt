package com.example.jetpacktest.entity

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

data class User(
    var name: ObservableField<String>,
    var age: ObservableInt,
    var layoutType: Int = 0

) {
    var index=0
    fun update() {
        index++
        age.set(index)
    }

}