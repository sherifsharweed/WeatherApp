package com.shekoo.iweather.ui.alert

import WeatherResponse
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.repo.AlertRepo
import com.shekoo.iweather.repo.WeatherRepo
import kotlinx.coroutines.launch

class AlertViewModel(context: Context) : ViewModel() {

    var weatherLiveData = MutableLiveData<WeatherResponse>()

    private val weatherRepo = WeatherRepo();

    fun getWeather(lat : Double ,lon :Double ,language: String , units : String) {
        weatherRepo.getWeather(lat , lon ,language, units)
        weatherLiveData = weatherRepo.weatherLiveData
    }

    val repo : AlertRepo = AlertRepo(WeatherDataBase.getInstance(context).getWeatherDao())

    fun getAllAlert() : LiveData<List<MyAlert>> {
        return repo.getAllAlerts()
    }

    fun insertAlarmItem(alert: MyAlert){
        viewModelScope.launch {
            repo.insertAlarmItem(alert)
        }
    }

    fun deleteAlarmItem(alert: MyAlert){
        viewModelScope.launch {
            repo.deleteAlarmItem(alert)
        }
    }

    fun deleteSpecificAlarm(first : Long){
        viewModelScope.launch {
            repo.deleteSpecificAlarm(first)
        }
    }

}