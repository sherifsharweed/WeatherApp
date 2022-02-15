package com.shekoo.iweather.data.local

import Daily
import Hourly
import Weather
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shekoo.iweather.model.Alerts
import java.lang.reflect.Type


object DataConverter {

    @TypeConverter
    fun hourlyListToString(hourlyList: List<Hourly>?): String? {
        if (hourlyList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Hourly>>(){}.type
        return gson.toJson(hourlyList, type)
    }

    @TypeConverter
    fun hourlyStringToList(hourlyString: String?): List<Hourly>? {
        if (hourlyString == null) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Hourly>>() {}.type
        return gson.fromJson(hourlyString, type)
    }

    @TypeConverter
    fun dailyListToString(dailyList: List<Daily>?): String? {
        if (dailyList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Daily>>(){}.type
        return gson.toJson(dailyList, type)
    }

    @TypeConverter
    fun dailyStringToList(dailyString: String?): List<Daily>? {
        if (dailyString == null) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Daily>>() {}.type
        return gson.fromJson(dailyString, type)
    }

    @TypeConverter
    fun weatherListToString(weatherList: List<Weather>?): String? {
        if (weatherList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather>>(){}.type
        return gson.toJson(weatherList, type)
    }

    @TypeConverter
    fun weatherStringToList(weatherString: String?): List<Weather>? {
        if (weatherString == null) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(weatherString, type)
    }

    @TypeConverter
    fun alertListToString(alertList: List<Alerts>?): String? {
        if (alertList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Alerts>>(){}.type
        return gson.toJson(alertList, type)
    }

    @TypeConverter
    fun alertStringToList(alertString: String?): List<Alerts>? {
        if (alertString == null) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Alerts>>() {}.type
        return gson.fromJson(alertString, type)
    }
}