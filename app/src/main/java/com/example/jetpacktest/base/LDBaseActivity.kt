package com.example.jetpacktest.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class LDBaseActivity<D : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: D
    @get:LayoutRes
    abstract val layoutId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        initView()
    }

    abstract fun initView()
}