package com.shekoo.iweather.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.Dao
import com.shekoo.iweather.model.Favorite

@Dao
interface Dao {


    @Insert()
   suspend fun insertFavoriteItem(favorite:Favorite)

    @Query("SELECT * FROM favorite_list")
    fun getAllFavorites() : LiveData<List<Favorite>>

    @Delete
    suspend fun deleteFavoriteItem(favorite: Favorite)


}