package com.shekoo.iweather.ui.alert

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlertViewModelFactory(var context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AlertViewModel::class.java)){
            return AlertViewModel(context) as T
        }
        throw IllegalArgumentException("view model class not found")
    }
}