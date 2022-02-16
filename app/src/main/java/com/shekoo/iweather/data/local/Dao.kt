package com.shekoo.iweather.data.local

import com.shekoo.iweather.model.WeatherResponse
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert

@Dao
interface Dao {

    //weatherresponse
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherResponse(weatherResponse: WeatherResponse)*/

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

    @Query("DELETE FROM alert_list WHERE first = :first")
    suspend fun deleteSpecificAlarm(first : Long )

    @Query("SELECT * FROM alert_list")
    fun getAllAlerts() : LiveData<List<MyAlert>>


}