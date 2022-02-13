package com.shekoo.iweather.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shekoo.iweather.R
import com.shekoo.iweather.databinding.ActivityMapForLocationBinding
import com.shekoo.iweather.ui.*

class MapForLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapForLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapForLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

        mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener{
            override fun onMapClick(p0: LatLng) {
                Log.i(TAG, "onMapClick: "+p0.longitude.toString())
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(p0))
                getLocation(p0)
            }
        })
    }

    fun getLocation(point : LatLng){
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        Log.i(TAG, "getLocation: "+point.longitude.toString())
        editor.putString(LATITUDE, point.latitude.toString())
        editor.putString(LONGITUDE , point.longitude.toString())
        editor.apply()
    }


    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        var location: String? = sharedPreferences.getString(LOCATION, "gps")
        var long: String? = sharedPreferences.getString(LONGITUDE, "0.0")
        Log.i(TAG, "ondes: " + location)
        Log.i(TAG, "ondes: " + long)

    }
}