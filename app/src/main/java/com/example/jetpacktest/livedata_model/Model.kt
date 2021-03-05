package com.example.jetpacktest.livedata_model

import androidx.lifecycle.*

class Model : ViewModel() {
    val livedata: LiveData<String>
        get() = Transformations.map(_lavedata) {
            it.toString()
        }
    private val _lavedata = MutableLiveData<Int>()


    val mediator = MediatorLiveData<Int>()

    var index = 0
    fun update() {
        index++
        _lavedata.value = index
        mediator.value = index
    }
}