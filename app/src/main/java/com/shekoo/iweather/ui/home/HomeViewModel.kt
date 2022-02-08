package com.shekoo.iweather.ui.home

import WeatherResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekoo.iweather.repo.WeatherRepo
import com.shekoo.iweather.ui.TAG

class HomeViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<WeatherResponse>()

    private val repo = WeatherRepo();

    fun getWeather(lat : Double ,lon :Double , units : String) {
        repo.getWeather(lat , lon , units)
        weatherLiveData = repo.weatherLiveData
        Log.i(TAG, "getWeather: 2"+weatherLiveData.value?.current?.clouds.toString())

    }

}