package com.shekoo.iweather.ui.alert

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.repo.AlertRepo
import com.shekoo.iweather.repo.FavoriteRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlertViewModel(context: Context) : ViewModel() {

    val repo : AlertRepo = AlertRepo(WeatherDataBase.getInstance(context).getWeatherDao())

    fun getAllAlert() : LiveData<List<MyAlert>> {
        return repo.getAllAlerts()
    }

    fun insertAlarmItem(alert: MyAlert){
        GlobalScope.launch {
            repo.insertAlarmItem(alert)
        }
    }
}