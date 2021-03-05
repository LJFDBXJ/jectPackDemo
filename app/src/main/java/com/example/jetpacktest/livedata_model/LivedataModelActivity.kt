package com.example.jetpacktest.livedata_model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.jetpacktest.R
import com.example.jetpacktest.databinding.ActivityLifecycleBinding
import com.example.jetpacktest.databinding.ActivityLivedataModelBinding

class LivedataModelActivity : AppCompatActivity() {
    private val model by viewModels<Model>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        val binding = ActivityLivedataModelBinding.inflate(inflater)
        setContentView(binding.root)
        model.livedata.observe(this) {
            binding.dataOne.text = it.toString()
        }
        model.mediator.hasActiveObservers()
        //mediator 添加 livedata 然后监听
        model.mediator.addSource(model.livedata) {
            binding.dataTwo.text = it
        }
//        model.mediator.observe(this){
//            Log.d("tag",it.toString())
//        }
        binding.update.setOnClickListener {
            model.update()
        }
        binding.remove.setOnClickListener {
            viewModelStore.clear()
        }
    }
}