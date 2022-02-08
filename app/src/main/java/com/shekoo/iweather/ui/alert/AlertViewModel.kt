package com.shekoo.iweather.ui.alert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlertViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is alert Fragment"
    }
    val text: LiveData<String> = _text
}