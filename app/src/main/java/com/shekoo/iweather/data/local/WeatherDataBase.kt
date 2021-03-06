package com.shekoo.iweather.data.local

import android.content.Context
import androidx.room.*
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.model.MyAlert
import com.shekoo.iweather.model.WeatherResponse

@Database(entities = [Favorite::class,MyAlert::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun getWeatherDao() : Dao

    companion object {
        @Volatile
        private var instance: WeatherDataBase? = null

         fun getInstance(context: Context) : WeatherDataBase {
             return instance ?: synchronized(Any()){
                 instance ?: createDatabase(context).also {
                     instance = it
                 }
             }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WeatherDataBase::class.java,
            "weatherdatabase").build()
    }
}