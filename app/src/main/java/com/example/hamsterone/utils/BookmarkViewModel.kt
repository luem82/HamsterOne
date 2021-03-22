package com.example.hamsterone.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookmarkViewModel : ViewModel() {

    internal var mutableLiveData = MutableLiveData<Int>()

    fun setCount(count: Int) {
        mutableLiveData.value = count
    }

    fun getCount(): MutableLiveData<Int> {
        return mutableLiveData
    }
}