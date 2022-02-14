package com.shekoo.iweather.data.local

import android.service.autofill.LuhnChecksumValidator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.Dao
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert

@Dao
interface Dao {



    //favorites
    @Insert()
   suspend fun insertFavoriteItem(favorite:Favorite)

    @Query("SELECT * FROM favorite_list")
    fun getAllFavorites() : LiveData<List<Favorite>>

    @Delete
    suspend fun deleteFavoriteItem(favorite: Favorite)

    //alarms
    @Insert()
    suspend fun insertAlarmItem(alert : MyAlert)

    @Delete
    suspend fun deleteAlarmItem(alert: MyAlert)

    @Query("SELECT * FROM alert_list")
    fun getAllAlerts() : LiveData<List<MyAlert>>


}