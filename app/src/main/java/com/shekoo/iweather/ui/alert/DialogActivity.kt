package com.shekoo.iweather.ui.alert

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shekoo.iweather.R
import com.shekoo.iweather.model.Alerts
import com.shekoo.iweather.ui.*
import com.shekoo.iweather.ui.favorite.FavoriteViewModelFactory
import com.shekoo.iweather.ui.home.HomeViewModel
import java.util.*

class DialogActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var alertViewModel: AlertViewModel
    var textforalarm : String? = "Weather is Good, there is no alerts"
    var listOfAlerts : List<Alerts>? = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alertViewModelFactory = AlertViewModelFactory(applicationContext)
        alertViewModel = ViewModelProvider(this,alertViewModelFactory).get(AlertViewModel::class.java)

        setContentView(R.layout.activity_dialog)
        var UNIX = intent.getLongExtra("KEY",0L)
        Log.i(TAG, "onCreate: "+UNIX)

        initViews()


        alertViewModel.weatherLiveData.observe(this, Observer {

            if(alertViewModel.weatherLiveData.value?.alerts?.size != null){
                var text = ""
                listOfAlerts = it.alerts
                for(i in 0 until listOfAlerts!!.size-1){
                    val calendar = Calendar.getInstance()
                    val timeMillis = calendar.timeInMillis
                    if(listOfAlerts!![i].start >= timeMillis || listOfAlerts!![i].end <= timeMillis){
                        text = text +"\n"+ listOfAlerts!![i].description
                    }
            }
                sendTextToDialog(text,UNIX)
            }else{
                sendTextToDialog(textforalarm,UNIX)
            }
        })
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.start()


    }
    private fun sendTextToDialog(textforalarm: String?, UNIX:Long) {

        val alertdialog = AlertDialog.Builder(this)
        alertdialog.setCancelable(false) // that make the dialog cant cancelled until u click inside the dialog itself

        alertdialog.setTitle("Weather")
        alertdialog.setMessage(textforalarm)

        alertdialog.setNegativeButton("OK") { dialogInterface, i ->
            dialogInterface.cancel()
            alertViewModel.deleteSpecificAlarm(UNIX)
            mediaPlayer.stop()
            finish()
        }

        alertdialog.create().window!!.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        alertdialog.show()
    }

    private fun initViews() {
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)!!
        val lat = sharedPreferences.getString(LATITUDE, "0.0" )?.toDouble()
        val lon = sharedPreferences.getString(LONGITUDE,"0.0")?.toDouble()
        val language = sharedPreferences.getString(LANGUAGE,"en").toString()
        if(lat != 0.0) {
            alertViewModel.getWeather(lat!!, lon!!, language, "metric")
        }
    }

}