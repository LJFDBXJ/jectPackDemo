package com.example.jetpacktest

import com.example.jetpacktest.base.LDBaseActivity
import com.example.jetpacktest.custom_view.ui.DragActivity
import com.example.jetpacktest.databinding.ActivityHomeBinding
import com.example.jetpacktest.databinding.ui.DataBindingActivity
import com.example.jetpacktest.entity.User
import com.example.jetpacktest.lifecycle.LifecycleActivity
import com.example.jetpacktest.livedata_model.LivedataModelActivity
import com.example.jetpacktest.udp.ReceiveActivity

class HomeActivity(override val layoutId: Int = R.layout.activity_home) :
    LDBaseActivity<ActivityHomeBinding>() {
    override fun initView() {
        val adapter = HomeAdapter()

        binding.homeRecycler.adapter = adapter
        val data = mutableListOf(
            DataBindingActivity::class.java,
            DragActivity::class.java,
            LifecycleActivity::class.java,
            LivedataModelActivity::class.java,
            ReceiveActivity::class.java,
        )
        adapter.submitList(data)
    }

}
