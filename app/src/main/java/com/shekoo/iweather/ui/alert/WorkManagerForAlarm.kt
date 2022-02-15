package com.shekoo.iweather.ui.alert

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shekoo.iweather.ui.TAG

class WorkManagerForAlarm(var context: Context, var workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val UNIX = inputData.getLong("KEY",0L)
        Log.i(TAG, "doWork: "+UNIX)
        val intent1 = Intent(context, DialogActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK // important for start activity from no one
        intent1.putExtra("KEY",UNIX)
        context.startActivity(intent1)
        return Result.success()
    }


}