package com.shekoo.iweather.ui.home

import Daily
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.iweather.R
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter (private val dailyTemp : List<Daily> , private val context : Context) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.daily_averageTemp_tv.text= dailyTemp.get(position).temp.max.toInt().toString()+"/"+dailyTemp.get(position).temp.min.toInt().toString()
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

}
