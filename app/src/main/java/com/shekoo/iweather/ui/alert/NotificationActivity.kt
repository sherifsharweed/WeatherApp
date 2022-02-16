package com.shekoo.iweather.ui.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.shekoo.iweather.R
import com.shekoo.iweather.databinding.ActivityNotificationBinding
import com.shekoo.iweather.model.Alerts
import com.shekoo.iweather.ui.FILE_NAME
import com.shekoo.iweather.ui.LANGUAGE
import com.shekoo.iweather.ui.LATITUDE
import com.shekoo.iweather.ui.LONGITUDE
import java.util.*

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var mediaPlayer: MediaPlayer


    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    private val NOTIFICATION_ID = 0
    private lateinit var alertViewModel: AlertViewModel
    var textforalarm : String? = "Weather is Good, there is no alerts"
    var listOfAlerts : List<Alerts>? = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mNotifyManager: NotificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alertViewModelFactory = AlertViewModelFactory(applicationContext)
        alertViewModel = ViewModelProvider(this,alertViewModelFactory).get(AlertViewModel::class.java)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var UNIX = intent.getLongExtra("KEY",0L)
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI)
        mediaPlayer.start()
        initViews()
        createNotificationChannel()

        alertViewModel.weatherLiveData.observe(this, androidx.lifecycle.Observer {

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
                sendNotification(text)
                binding.notificationText.text = text
            }else{
                sendNotification(textforalarm)
                binding.notificationText.text = textforalarm
            }
        })

        //setContentView(binding.root)

        binding.notificationOkButton.setOnClickListener {
            alertViewModel.deleteSpecificAlarm(UNIX)
            mNotifyManager.cancel(NOTIFICATION_ID);
            mediaPlayer.stop()
            finish()
        }



    }
    fun sendNotification(text : String?) {
        val mNotifyManager: NotificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyBuilder  = NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL_ID)
            .setContentTitle("iWeather")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_baseline_cloud_24)
            .setAutoCancel(true)
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
    }

    fun createNotificationChannel() {
        val mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // to check the version of API of device as notification channel statrt from API26 version_Codes_O ((O-> oreo))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create a NotificationChannel with ID that declared above and name that name for
            // the notification setting in app setting ,, importance high for shows everywhere, makes noise and peeks.
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID,
                "com.shekoo.iweather.model.Weather Notification", NotificationManager.IMPORTANCE_DEFAULT)
            //set up setting for notification of this channel
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true) // to show dot on app icon
            notificationChannel.description = "Notification from Trip"
            mNotifyManager.createNotificationChannel(notificationChannel)
        }
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