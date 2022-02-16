package com.shekoo.iweather.repo

import com.shekoo.iweather.model.WeatherResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shekoo.iweather.data.local.Dao
import com.shekoo.iweather.data.remote.API_KEY
import com.shekoo.iweather.data.remote.EXCLUDE
import com.shekoo.iweather.data.remote.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class WeatherRepo() {
    private val TAG = "main"
    val weatherLiveData = MutableLiveData<WeatherResponse>()


    fun getWeather( lat : Double ,lon :Double ,language : String , units : String ){
        RetrofitClient.getApiService().getWeather(lat, lon, EXCLUDE ,language, units , API_KEY).enqueue(object : retrofit2.Callback<WeatherResponse> {
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