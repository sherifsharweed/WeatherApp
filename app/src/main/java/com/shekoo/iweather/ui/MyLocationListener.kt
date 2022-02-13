package com.shekoo.iweather.ui

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.Toast


class MyLocationListener(var context : Context) : LocationListener {
    var wayLatitude = 0.0
    var wayLongitude = 0.0

    override fun onLocationChanged(p0: Location) {
        wayLatitude = p0.latitude
        wayLongitude = p0.longitude
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        var location : String? = sharedPreferences.getString(LOCATION,"gps")
        if(location =="gps"){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LATITUDE, wayLatitude.toString())
        editor.putString(LONGITUDE , wayLongitude.toString())
        editor.apply()
    }
    }

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}