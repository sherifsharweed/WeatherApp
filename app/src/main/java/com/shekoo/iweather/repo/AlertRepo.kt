package com.shekoo.iweather.repo

import androidx.lifecycle.LiveData
import com.shekoo.iweather.data.local.Dao
import com.shekoo.iweather.model.MyAlert

class AlertRepo(private var dao : Dao) {

    suspend fun insertAlarmItem(alert: MyAlert){
        dao.insertAlarmItem(alert)
    }

    suspend fun deleteAlarmItem(alert: MyAlert){
        dao.deleteAlarmItem(alert)
    }

    fun getAllAlerts(): LiveData<List<MyAlert>>{
        return dao.getAllAlerts()
    }

    suspend fun deleteSpecificAlarm (first : Long){
        dao.deleteSpecificAlarm(first)
    }
}