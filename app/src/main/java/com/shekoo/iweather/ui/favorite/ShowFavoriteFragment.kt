package com.shekoo.iweather.ui.favorite

import com.shekoo.iweather.model.Hourly
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.iweather.databinding.FragmentHomeBinding
import com.shekoo.iweather.model.Daily
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.ui.*
import com.shekoo.iweather.ui.home.DailyAdapter
import com.shekoo.iweather.ui.home.HomeViewModel
import com.shekoo.iweather.ui.home.HourlyAdapter
import java.io.IOException
import java.text.SimpleDateFormat
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
        initviews()
        observeViewModel()
    }

    private fun initviews() {
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        val language = sharedPreferences.getString(LANGUAGE,"en").toString()
        homeViewModel.getWeather(favorite.favourite_latitude,favorite.favourite_longitude,language,"metric")
    }

    private fun observeViewModel() {
        homeViewModel.weatherLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding?.tempTv?.text= setTemp(it.current.temp.toInt())
            binding?.dateTv?.text = setDate (it.current.dt)
            binding?.timeTv?.text = setTime(it.current.dt)
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

            binding?.humidityTv?.text =  it.current.humidity.toString()
            binding?.pressureTv?.text = it.current.pressure.toString()
            binding?.cloudsTv?.text =it.current.clouds.toString()
            binding?.windspeedTv?.text = setWindSpeed(it.current.wind_speed)
            binding?.unitOfWindSpeed?.text = editunit()


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

    fun setTemp(temp : Int): String{
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        var tempDegree = sharedPreferences.getString(TEMPDEGREE,"celsius").toString()
        if(tempDegree=="celsius") {
            return (temp.toString() + "°C")
        }else if (tempDegree=="fehrnhit"){
            return ((temp*1.8+32).toInt().toString()+"°F")
        }else
            return ((temp+273).toString()+"°K")
    }

    fun setWindSpeed(wind : Double) : String{
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        var tempDegree = sharedPreferences.getString(WINDSPEED,"metric").toString()
        if(tempDegree == "metric"){
            return (wind.toInt().toString())
        }else{
            return ((wind*2.236).toInt().toString())
    }}

    fun editunit() : String {
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        val tempDegree = sharedPreferences.getString(WINDSPEED,"metric").toString()
        val language = sharedPreferences.getString(LANGUAGE,"en").toString()
        Log.i(TAG, "editunit: "+tempDegree)
        if(tempDegree == "mile"){
            if(language=="en"){
                return ("mile/hr")
            }else
                return("ميل/ساعة")
        }else{
            if(language =="en"){
                return ("m/sec")
            }else
                return ("متر/ث")
        }
    }

    fun setDate( date : Int) : String{
        val netDate = Date(date.toLong() * 1000)
        val sdf: SimpleDateFormat = SimpleDateFormat("EEE, MMM d")
        return sdf.format(netDate)
    }

    fun setTime(time : Int) : String {
        val netDate = Date(time.toLong() * 1000)
        val sdf: SimpleDateFormat = SimpleDateFormat("h:mm a")
        return sdf.format(netDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}