package com.shekoo.iweather.ui.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shekoo.iweather.R
import com.shekoo.iweather.ui.TAG

class WorkManagerForNotification (var context: Context, var workerParams: WorkerParameters)
                                                          : Worker(context, workerParams) {
        override fun doWork(): Result {
            val UNIX = inputData.getLong("KEY",0L)
            Log.i(TAG, "doWork: "+UNIX)
            val intent1 = Intent(context, NotificationActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK // important for start activity from no one
            intent1.putExtra("KEY",UNIX)
            context.startActivity(intent1)
        return Result.success()
    }
}