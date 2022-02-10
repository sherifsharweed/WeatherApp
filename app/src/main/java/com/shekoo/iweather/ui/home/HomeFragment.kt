package com.shekoo.iweather.ui.home

import Daily
import Hourly
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.iweather.databinding.FragmentHomeBinding
import com.shekoo.iweather.ui.FILE_NAME
import com.shekoo.iweather.ui.LATITUDE
import com.shekoo.iweather.ui.LONGITUDE
import com.shekoo.iweather.ui.TAG
import java.io.IOException
import java.util.*

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel

    lateinit var myHourlyAdapter: HourlyAdapter
    lateinit var myDailyAdapter: DailyAdapter

    lateinit var recyclerViewHorizontal: RecyclerView
    lateinit var recyclerViewVertical: RecyclerView

    lateinit var hourlyTemp: List<Hourly>
    lateinit var dailyTemp: List<Daily>

    var city : String? = ""
    lateinit var country :String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding?.root!!

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }
    private fun observeViewModel() {
        homeViewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "observeViewModel: 2")
            binding?.tempTv?.text= it.current.temp.toInt().toString()
            binding?.descriptionTv?.text = it.current.weather.get(0).description
            context?.let { context ->
                var link ="http://openweathermap.org/img/wn/"+ it.current?.weather.get(0).icon+"@2x.png"
                Glide.with(context).load(link).into(binding?.weatherIconIv!!)
            }
            val gcd = Geocoder(context, Locale.getDefault())
            var address: List<Address>?
            try {
                address = gcd.getFromLocation(it.lat , it.lon ,2)
                Log.i(TAG, "observeViewModel: "+address)
                city = address[0].locality?.toString()
                country = address[0].countryName
            } catch (e: IOException) {
                e.printStackTrace()
            }
            binding?.cityTv?.text = city +",\n " + country

            binding?.humidityTv?.text = "Humidity \n"+ it.current.humidity.toString()
            binding?.pressureTv?.text = "Pressure \n" +it.current.pressure.toString()
            binding?.cloudsTv?.text =" Clouds \n " +it.current.clouds.toString()
            binding?.windspeedTv?.text = "WindSpeed\n"+ it.current.wind_speed.toString()
            binding?.visibilityTv?.text ="Visibility\n"+ it.current.visibility.toString()
            binding?.sunsetSunriseTv?.text=it.current.sunrise.toString()

            ////hourly recycler
            val layoutManagerForHourly = LinearLayoutManager(view?.context, RecyclerView.HORIZONTAL, false)

            hourlyTemp = it.hourly
            myHourlyAdapter = HourlyAdapter(hourlyTemp, view?.context!!)
            recyclerViewHorizontal = binding?.hourlyRecyclerView!!
            recyclerViewHorizontal.adapter = myHourlyAdapter
            recyclerViewHorizontal.layoutManager = layoutManagerForHourly

            /// daily recycler
            val layoutManagerForDaily = LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)

            dailyTemp = it.daily
            myDailyAdapter = DailyAdapter(dailyTemp,view?.context!!)
            recyclerViewVertical = binding?.dailyRecyclerView!!
            recyclerViewVertical.adapter = myDailyAdapter
            recyclerViewVertical.layoutManager = layoutManagerForDaily
        })
    }

    private fun initViews() {
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        val lat = sharedPreferences.getString(LATITUDE, "60.7392213" )?.toDouble()
        val lon = sharedPreferences.getString(LONGITUDE,"8.3567771")?.toDouble()
        homeViewModel.getWeather(lat!!,lon!!,"metric")
        Log.i(TAG, "initViews: " + lat)
        Log.i(TAG, "initViews: "+lon)

        //homeViewModel.getWeather(30.4646308,30.9349903,"metric")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}