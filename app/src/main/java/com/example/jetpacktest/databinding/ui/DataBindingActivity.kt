package com.example.jetpacktest.databinding.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.*
import com.example.jetpacktest.R
import com.example.jetpacktest.databinding.ActivityMainBinding
import com.example.jetpacktest.databinding.MyHandler
import com.example.jetpacktest.databinding.ObservableUser
import com.example.jetpacktest.entity.User

class DataBindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val observableUser = ObservableUser()
    private val userList = ArrayList<User>()
    private val user = User(ObservableField("aaa"), ObservableInt(2))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //第一种使用方式 第三个参数 传入实现 BindAdapter ：DataBindingComponent  实例
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main, BindAdapter())
        //第二种使用方式  不传第三个参数，但是需要静态方法
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


//      第一种传入binding 实例
        binding.handler = MyHandler()


//      第二种 实现 BaseObservable
        binding.observer = observableUser


//      第三种 使用 实现BaseObservable  的各种子类  如 ObservableField   ObservableInt  。。。。
        binding.user = user

        binding.data = userList
        for (i in 0..20) {
            userList.add(User(ObservableField("Name"), ObservableInt(i)))
        }
    }

}