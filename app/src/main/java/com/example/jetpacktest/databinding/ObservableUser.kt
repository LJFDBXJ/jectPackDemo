package com.example.jetpacktest.databinding

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class ObservableUser : BaseObservable() {

    var index = 0

    //Bindable must be on a member in an Observable
    @get:Bindable
    var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var numberOfSets: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.numberOfSets)
        }
        get() {
            Log.d("tag", field.toString())
            return field
        }


    fun click() {
        index++
        firstName = index.toString()
    }

}