package com.shekoo.iweather.ui.favorite

import Daily
import Hourly
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.iweather.R
import com.shekoo.iweather.databinding.FragmentHomeBinding
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.ui.FILE_NAME
import com.shekoo.iweather.ui.LATITUDE
import com.shekoo.iweather.ui.LONGITUDE
import com.shekoo.iweather.ui.TAG
import com.shekoo.iweather.ui.home.DailyAdapter
import com.shekoo.iweather.ui.home.HomeViewModel
import com.shekoo.iweather.ui.home.HourlyAdapter
import java.io.IOException
import java.util.*


class ShowFavoriteFragment(val favorite: Favorite) : Fragment() {


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
        homeViewModel.getWeather(favorite.favourite_latitude,favorite.favourite_longitude,"en","metric")
        observeViewModel()
    }
    private fun observeViewModel() {
        homeViewModel.weatherLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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


            ////hourly recycler
            val layoutManagerForHourly = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            hourlyTemp = it.hourly
            myHourlyAdapter = HourlyAdapter(hourlyTemp, requireContext())
            recyclerViewHorizontal = binding?.hourlyRecyclerView!!
            recyclerViewHorizontal.adapter = myHourlyAdapter
            recyclerViewHorizontal.layoutManager = layoutManagerForHourly

            /// daily recycler
            val layoutManagerForDaily = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            dailyTemp = it.daily
            myDailyAdapter = DailyAdapter(dailyTemp,requireContext())
            recyclerViewVertical = binding?.dailyRecyclerView!!
            recyclerViewVertical.adapter = myDailyAdapter
            recyclerViewVertical.layoutManager = layoutManagerForDaily
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}