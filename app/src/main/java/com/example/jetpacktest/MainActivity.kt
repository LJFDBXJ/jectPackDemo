package com.example.jetpacktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.*
import com.example.jetpacktest.databinding.ActivityMainBinding
import com.example.jetpacktest.entity.ObservableUser
import com.example.jetpacktest.entity.User
import com.example.jetpacktest.utle.MyHandler

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val observableUser = ObservableUser()
    private val userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.data = userList
        binding.handler = MyHandler()
        binding.observer = observableUser
        for (i in 0..20) {
            userList.add(User(i.toString(),i))
        }

    }

    var position = 0
    override fun onClick(v: View?) {
        position++
        when (v) {
            binding.plus -> {
                observableUser.firstName = position.toString()
            }
        }
    }
}