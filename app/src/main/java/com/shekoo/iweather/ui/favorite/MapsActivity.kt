package com.shekoo.iweather.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.shekoo.iweather.R
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.databinding.ActivityMapsBinding
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.repo.FavoriteRepo
import com.shekoo.iweather.ui.TAG
import kotlinx.coroutines.launch


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {

    private lateinit var myMap: GoogleMap
    lateinit var repo : FavoriteRepo
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteViewModelFactory = FavoriteViewModelFactory(applicationContext)
        favoriteViewModel = ViewModelProvider(this,favoriteViewModelFactory).get(FavoriteViewModel::class.java)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
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
        myMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        myMap.addMarker(MarkerOptions().position(sydney))
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        myMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

        myMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener{
            override fun onMapClick(p0: LatLng) {
                myMap.clear()
                myMap.addMarker(MarkerOptions().position(p0))
                showDialog(p0)
            }
        })

    }

    override fun onInfoWindowClick(p0: Marker) {
        Toast.makeText(this, p0.title, Toast.LENGTH_LONG).show();
    }

    fun showDialog(p0: LatLng){
        val latitude = p0.latitude
        val longitude = p0.longitude
        val alertdialog = AlertDialog.Builder(this)
        alertdialog.setCancelable(false) // that make the dialog cant cancelled until u click inside the dialog itself
        alertdialog.setTitle("Save")
        alertdialog.setMessage("Do you want to save this place to favorites?")
        alertdialog.setNegativeButton("Cancel") { dialogInterface, i ->
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            dialogInterface.cancel()
        }
        alertdialog.setPositiveButton("Save") { dialogInterface, i ->
            Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "showDialog: " + latitude+","+longitude)
            favoriteViewModel.insertFavoriteItem(Favorite(latitude,longitude))
            finish()
        }
        alertdialog.create()
        alertdialog.show()
    }




}