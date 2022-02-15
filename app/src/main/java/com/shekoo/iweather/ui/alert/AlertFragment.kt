package com.shekoo.iweather.ui.alert

import WeatherResponse
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.databinding.FragmentAlertBinding
import com.shekoo.iweather.databinding.FragmentSettingBinding
import com.shekoo.iweather.model.Alerts
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.repo.AlertRepo
import com.shekoo.iweather.ui.*
import com.shekoo.iweather.ui.alert.AlertViewModel
import com.shekoo.iweather.ui.favorite.FavoriteAdapter
import com.shekoo.iweather.ui.favorite.MapsActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlertFragment : Fragment() {

    private var _binding: FragmentAlertBinding? = null
    private lateinit var alertViewModel: AlertViewModel

    private lateinit var alertAdapter: AlertAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var alertList: List<MyAlert>
    private lateinit var weatherResponse: WeatherResponse


    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentAlertBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val alertViewModelFactory = AlertViewModelFactory(requireContext())
        alertViewModel = ViewModelProvider(this,alertViewModelFactory).get(AlertViewModel::class.java)

        val layoutManagerForFavorite = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView = binding.alertRecyclerView
        recyclerView.layoutManager = layoutManagerForFavorite

        binding.fab.setOnClickListener {
            val intent = Intent(context, AddAlertActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeViewModel()
    }

    private fun observeViewModel() {
        alertViewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            weatherResponse = it
            alertViewModel.getAllAlert().observe(viewLifecycleOwner, Observer {
                alertList = it
                alertAdapter = AlertAdapter(alertList, requireContext(),alertViewModel::deleteAlarmItem)
                recyclerView.adapter = alertAdapter
1
            })
        })
    }

    private fun init(){
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        val lat = sharedPreferences.getString(LATITUDE, "0.0" )?.toDouble()
        val lon = sharedPreferences.getString(LONGITUDE,"0.0")?.toDouble()
        val language = sharedPreferences.getString(LANGUAGE,"en").toString()
        if(lat != 0.0) {
            alertViewModel.getWeather(lat!!, lon!!, language, "metric")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}