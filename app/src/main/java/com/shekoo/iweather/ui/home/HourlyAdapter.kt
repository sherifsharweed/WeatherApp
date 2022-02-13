package com.shekoo.iweather.ui.home

import Hourly
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.iweather.R
import com.shekoo.iweather.ui.FILE_NAME
import com.shekoo.iweather.ui.TEMPDEGREE
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter (private val hourlyTemp : List<Hourly> , private val context : Context) :
    RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hourly_temp_tv.text= setTemp(hourlyTemp.get(position).temp.toInt())
        holder.hourly_description_tv.text=hourlyTemp.get(position).weather.get(0).description
        context?.let { context ->
            var link ="http://openweathermap.org/img/wn/"+ hourlyTemp.get(position).weather.get(0).icon+"@2x.png"
            Glide.with(context).load(link).into(holder.hourly_icon_iv)
        }
        val netDate = Date((hourlyTemp.get(position).dt.toString()).toLong() * 1000)
        val sdf: SimpleDateFormat = SimpleDateFormat("h a")
        holder.hourly_hour_tv.text = sdf.format(netDate)
    }

    override fun getItemCount(): Int {

        return hourlyTemp.size
    }

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item) {
        val hourly_hour_tv : TextView
            get() = item.findViewById(R.id.hourly_hour_tv)

        val hourly_temp_tv : TextView
            get() = item.findViewById(R.id.hourly_temp_tv)

        val hourly_description_tv : TextView
            get() = item.findViewById(R.id.hourly_description_tv)

        val hourly_icon_iv: ImageView
            get() = item.findViewById(R.id.hourly_icon_iv)


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


}