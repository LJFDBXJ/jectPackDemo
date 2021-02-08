package com.example.jetpacktest

import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpacktest.entity.User
import com.example.jetpacktest.ui.TestAdapter

@BindingMethods(
    value = [
        BindingMethod(
            type = AppCompatImageView::class,
            attribute = "tint",
            method = "setTint"
        )
    ]
)
object BindAdapter {
    @BindingAdapter("recyclerList", "error", requireAll = false)
    @JvmStatic
    fun RecyclerView.recyclerList(age: ArrayList<User>, error: String?) {
        val adapterTest = TestAdapter()
        adapter = adapterTest
        adapterTest.submitList(age)
    }

    fun setTint(color: Int) {

    }
}