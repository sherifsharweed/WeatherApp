package com.shekoo.iweather.ui.alert

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.iweather.R
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.repo.AlertRepo
import com.shekoo.iweather.repo.FavoriteRepo
import kotlinx.coroutines.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AlertAdapter (private val alertList : List<MyAlert>, private val context : Context) :
    RecyclerView.Adapter<AlertAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alert_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.firstAlertTime.text = returnTimeFormat(alertList.get(position).first)
        holder.secondAlertTime.text = returnTimeFormat(alertList.get(position).last)


        holder.alert_delete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val alertdialog = AlertDialog.Builder(context)
                alertdialog.setCancelable(false) // that make the dialog cant cancelled until u click inside the dialog itself
                alertdialog.setTitle("Delete")
                alertdialog.setMessage("Do you want to delete this alert?")
                alertdialog.setPositiveButton("Cancel") { dialogInterface, i ->
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                    dialogInterface.cancel()
                }
                alertdialog.setNegativeButton("Yes") { dialogInterface, i ->
                    Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show()
                    deleteAlert(alertList.get(holder.adapterPosition))
                }
                alertdialog.create()
                alertdialog.show()
            }
        })

    }

    override fun getItemCount(): Int {
        return alertList.size
    }

    inner class ViewHolder(val item : View) : RecyclerView.ViewHolder(item) {
        val firstAlertTime : TextView
            get() = item.findViewById(R.id.firstAlertTime)

        val secondAlertTime : TextView
            get() = item.findViewById(R.id.secondAlertTime)

        val alert_delete : ImageView
        get() = item.findViewById(R.id.alert_delete)



    }

    fun deleteAlert(alert: MyAlert) {
         lateinit var repo: AlertRepo

         GlobalScope.launch {
             repo = AlertRepo(WeatherDataBase.getInstance(context).getWeatherDao())
             repo.deleteAlarmItem(alert)

         }
     }



    fun returnTimeFormat(unix : Long) : String{
        val netDate = Date(unix * 1000)
        val sdf: SimpleDateFormat = SimpleDateFormat("EEE, MMM d, h:mm a")
        return sdf.format(netDate)
    }


}