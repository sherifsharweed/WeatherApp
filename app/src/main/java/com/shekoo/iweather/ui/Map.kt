package com.shekoo.iweather.ui

import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker

class Map : OnMapReadyCallback , GoogleMap.OnMarkerDragListener {
    lateinit var myMap : GoogleMap
    override fun onMapReady(p0: GoogleMap) {
        myMap = p0
    }

    override fun onMarkerDrag(p0: Marker) {
        Log.i("System out", "onMarkerDragStart..."+p0.position.latitude+"..."+p0.position.longitude)
    }

    override fun onMarkerDragEnd(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragStart(p0: Marker) {
        TODO("Not yet implemented")
    }
}