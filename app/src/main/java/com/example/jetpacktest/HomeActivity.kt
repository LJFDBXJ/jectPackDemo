package com.example.jetpacktest

import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpacktest.base.LDBaseActivity
import com.example.jetpacktest.custom_view.ui.DragActivity
import com.example.jetpacktest.databinding.ActivityHomeBinding
import com.example.jetpacktest.databinding.ui.DataBindingActivity
import com.example.jetpacktest.ext.toast
import com.example.jetpacktest.lifecycle.LifecycleActivity
import com.example.jetpacktest.livedata_model.LivedataModelActivity
import com.example.jetpacktest.udp.LocationActivity
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
            LocationActivity::class.java,
        )
        adapter.submitList(data)
        var changeLayoutTag = 0
        binding.changeHeadOrFoot.setOnClickListener {
            when (changeLayoutTag % 3) {
                0 -> {
                    toast("inset Head layout id")
                    adapter.headLayoutId = R.layout.item_inset_head
                }
                1 -> {
                    toast("inset Foot layout id")
                    adapter.footLayoutId = R.layout.item_inset_foot
                }
                else -> {
                    adapter.footLayoutId = null
                    adapter.headLayoutId = null
                    adapter.notifyDataSetChanged()
                }
            }
            changeLayoutTag++
        }
        var isCheck = false
        binding.changeManager.setOnClickListener {
            if (isCheck) {
                val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                binding.homeRecycler.layoutManager = manager
            } else {
                val manager = GridLayoutManager(this, 2,RecyclerView.VERTICAL,false)
                binding.homeRecycler.layoutManager = manager
            }
            isCheck = !isCheck
        }
    }

}
