package com.shekoo.iweather.ui.setting

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shekoo.iweather.databinding.FragmentSettingBinding
import com.shekoo.iweather.ui.*
import java.util.*


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    private lateinit var locationManager : LocationManager
    private lateinit var myLocationListener : MyLocationListener

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingViewModel = ViewModelProvider(this).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        myLocationListener = MyLocationListener(requireContext())


        val root: View = binding.root

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        var language : String? = sharedPreferences.getString(LANGUAGE,"en")
        when(language){
            "ar" -> binding.arabicRadiobutton.isChecked = true
            "en" -> binding.englishRadiobutton.isChecked = true
        }
        var tempDegree : String? = sharedPreferences.getString(TEMPDEGREE,"celsius")
        when(tempDegree){
            "celsius" -> binding.celsiusRadiobutton.isChecked = true
            "fehrnhit" -> binding.fehrnhitRadiobutton.isChecked = true
            "kelvin" -> binding.kelfinRadiobutton.isChecked = true
        }
        var windSpeed : String? = sharedPreferences.getString(WINDSPEED,"metric")
        when(windSpeed){
            "metric" -> binding.metricRadiobutton.isChecked = true
            "mile" -> binding.mileRadiobutton.isChecked = true
        }
        var location : String? = sharedPreferences.getString(LOCATION,"gps")
        when(location){
            "gps" -> binding.gpsRadiobutton.isChecked = true
            "map" -> binding.mapRadiobutton.isChecked = true
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arabicRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(LANGUAGE,"ar")
                    editor.apply()
                    setLocal(activity!! , "ar")

                }
            }
        })
        binding.englishRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(LANGUAGE,"en")
                    editor.apply()
                    setLocal(activity!! , "en")

                }
            }
        })
        binding.celsiusRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(TEMPDEGREE,"celsius")
                    editor.apply()
                }
            }
        })
        binding.fehrnhitRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(TEMPDEGREE,"fehrnhit")
                    editor.apply()
                }
            }
        })
        binding.kelfinRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(TEMPDEGREE,"kelvin")
                    editor.apply()
                }
            }
        })
        binding.metricRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(WINDSPEED,"metric")
                    editor.apply()
                }
            }
        })
        binding.mileRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(WINDSPEED,"mile")
                    editor.apply()
                }
            }
        })
        binding.gpsRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(LOCATION,"gps")
                    editor.apply()
                    //checkcurrentlocation(context!!)
                }
            }
        })
        binding.mapRadiobutton.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(LOCATION,"map")
                    editor.apply()
                    openMap()
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setLocal(activity: Activity, langCode: String?) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        /*when(langCode){
            "ar" ->  activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL)
            "en" ->  activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR)
        }*/
        val refresh = Intent(activity.applicationContext, MainActivity::class.java)
        startActivity(refresh)
        activity.finish()
    }

    fun openMap(){
        locationManager.removeUpdates(myLocationListener)
        var intent = Intent(this.context,MapForLocationActivity::class.java)
        startActivity(intent)
    }

    fun checkcurrentlocation(context: Context){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, LOCATION_CODE)
            return
        }else{
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            var location : String? = sharedPreferences.getString(LOCATION,"gps")
            if(location =="gps"){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,myLocationListener)
            }
        }
    }

}