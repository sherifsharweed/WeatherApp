package com.shekoo.iweather.ui.home

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
import com.shekoo.iweather.model.Daily
import com.shekoo.iweather.ui.FILE_NAME
import com.shekoo.iweather.ui.TEMPDEGREE
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter (private val dailyTemp : List<Daily>, private val context : Context) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.daily_averageTemp_tv.text= setTemp(dailyTemp.get(position).temp.max.toInt(),dailyTemp.get(position).temp.min.toInt())
        holder.daily_description_tv.text=dailyTemp.get(position).weather.get(0).description
        context?.let { context ->
            var link ="http://openweathermap.org/img/wn/"+ dailyTemp.get(position).weather.get(0).icon+"@2x.png"
            Glide.with(context).load(link).into(holder.daily_icon)
        }
        val netDate = Date((dailyTemp.get(position).dt.toString()).toLong() * 1000)
        val sdf: SimpleDateFormat = SimpleDateFormat("EEE, MMM d")
        holder.daily_date_tv.text = sdf.format(netDate)
    }

    override fun getItemCount(): Int {
        return dailyTemp.size
    }


    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item) {
        val daily_date_tv : TextView
            get() = item.findViewById(R.id.favortie_name_tv)

        val daily_description_tv : TextView
            get() = item.findViewById(R.id.daily_description_tv)

        val daily_averageTemp_tv : TextView
            get() = item.findViewById(R.id.daily_averageTemp_tv)

        val daily_icon: ImageView
            get() = item.findViewById(R.id.daily_icon)
    }

    fun setTemp(temp : Int , temp2:Int): String{
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)!!
        var tempDegree = sharedPreferences.getString(TEMPDEGREE,"celsius").toString()
        if(tempDegree=="celsius") {
            return (temp.toString()+"/"+temp2.toString() + "°C")
        }else if (tempDegree=="fehrnhit"){
            return ((temp*1.8+32).toInt().toString()+"/"+(temp2*1.8+32).toInt().toString()+"°F")
        }else
            return ((temp+273).toString()+"/"+(temp2+273).toString()+"°K")
    }

}
