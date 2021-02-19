package com.example.jetpacktest

import com.example.jetpacktest.base.LDBaseActivity
import com.example.jetpacktest.custom_view.ui.DragActivity
import com.example.jetpacktest.databinding.ActivityHomeBinding
import com.example.jetpacktest.databinding.ui.DataBindingActivity

class HomeActivity(override val layoutId: Int = R.layout.activity_home) :
    LDBaseActivity<ActivityHomeBinding>() {
    override fun initView() {
        val adapter = HomeAdapter()
        binding.homeRecycler.adapter = adapter
        val data = mutableListOf(
            DataBindingActivity::class.java,
            DragActivity::class.java
        )
        adapter.submitList(data)
    }

}
