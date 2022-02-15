package com.shekoo.iweather.repo

import androidx.lifecycle.LiveData
import com.shekoo.iweather.data.local.Dao
import com.shekoo.iweather.model.Favorite

class FavoriteRepo(private var dao : Dao) {

   suspend fun insertFavoriteItem(favorite: Favorite){
        dao.insertFavoriteItem(favorite)
    }

     fun getAllFavorites() : LiveData<List<Favorite>> {
         return dao.getAllFavorites()
    }

    suspend fun deleteFavoriteItem(favorite: Favorite){
        dao.deleteFavoriteItem(favorite)
    }

}