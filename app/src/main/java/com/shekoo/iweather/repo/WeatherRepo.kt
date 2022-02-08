package com.shekoo.iweather.repo

import WeatherResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shekoo.iweather.data.remote.API_KEY
import com.shekoo.iweather.data.remote.EXCLUDE
import com.shekoo.iweather.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class WeatherRepo {
    private val TAG = "main"
    val weatherLiveData = MutableLiveData<WeatherResponse>()

    fun getWeather( lat : Double ,lon :Double , units : String ){
        RetrofitClient.getApiService().getWeather(lat, lon, EXCLUDE , units , API_KEY).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        weatherLiveData.value= it
                        Log.i(TAG, "onResponse: "+it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                t.message?.let {
                    Log.i(TAG, "onFailure: $it")
                }
            }

        })
    }

}