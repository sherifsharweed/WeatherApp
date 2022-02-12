package com.shekoo.iweather.data.remote

import WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/onecall")

    fun getWeather(@Query("lat") lat: Double , @Query("lon") long: Double,
                   @Query("exclude") exclude : String,
                   @Query("lang") language : String,
                   @Query("units") units : String ,
                   @Query("appid") appid : String): Call<WeatherResponse>

}