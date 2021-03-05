package com.example.jetpacktest.lifecycle

import android.util.Log
import androidx.lifecycle.*

class CustomLifecycle : LifecycleEventObserver {

    //class CustomLifecycle : LifecycleObserver  会走注解方法
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onStart() {
        Log.d("tag", " event.name")

    }

    // class CustomLifecycle : LifecycleEventObserver  会走onStateChanged   不会走 注解方法
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("tag", event.name)
    }
}
