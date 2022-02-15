package com.shekoo.iweather.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shekoo.iweather.data.local.WeatherDataBase
import com.shekoo.iweather.model.Favorite
import com.shekoo.iweather.repo.FavoriteRepo
import com.shekoo.iweather.repo.WeatherRepo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class FavoriteViewModel(context: Context) : ViewModel() {

     val repo : FavoriteRepo = FavoriteRepo(WeatherDataBase.getInstance(context).getWeatherDao())

    fun getAllFavorites() : LiveData<List<Favorite>> {
        return repo.getAllFavorites()
    }

    fun deleteFavoriteItem(favorite: Favorite){
        viewModelScope.launch {
            repo.deleteFavoriteItem(favorite)
        }
    }

    fun insertFavoriteItem(favorite: Favorite){
        viewModelScope.launch {
            repo.insertFavoriteItem(favorite)
        }
    }



}