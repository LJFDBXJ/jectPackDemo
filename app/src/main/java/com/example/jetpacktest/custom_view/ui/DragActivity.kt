package com.example.jetpacktest.custom_view.ui

import com.example.jetpacktest.R
import com.example.jetpacktest.base.LDBaseActivity
import com.example.jetpacktest.databinding.ActivityDragHomeBinding
import com.example.jetpacktest.databinding.MyHandler

class DragActivity(override val layoutId: Int = R.layout.activity_drag_home) :
    LDBaseActivity<ActivityDragHomeBinding>() {
    override fun initView() {
        binding.viewModel = MyHandler()
    }

}