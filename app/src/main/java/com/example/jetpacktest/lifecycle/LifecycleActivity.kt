package com.example.jetpacktest.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetpacktest.R

class LifecycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        lifecycle.addObserver(CustomLifecycle())
    }
}