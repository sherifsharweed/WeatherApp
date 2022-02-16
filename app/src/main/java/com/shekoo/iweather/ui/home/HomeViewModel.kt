package com.shekoo.iweather.ui.home

import com.shekoo.iweather.model.WeatherResponse
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.repo.WeatherRepo

class HomeViewModel() : ViewModel() {

    var weatherLiveData = MutableLiveData<WeatherResponse>()

    private val repo = WeatherRepo();

    fun getWeather(lat : Double ,lon :Double ,language: String , units : String) {
        repo.getWeather(lat , lon ,language, units)
        weatherLiveData = repo.weatherLiveData
    }

}