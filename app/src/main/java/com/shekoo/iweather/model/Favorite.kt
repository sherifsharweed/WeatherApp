package com.shekoo.iweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "favorite_list")

class Favorite(
    @ColumnInfo(name = "latitude")
    var favourite_latitude: Double,
    @ColumnInfo(name = "longitude")
    var favourite_longitude: Double) {
   @PrimaryKey(autoGenerate = true)
   var favorite_id : Int = 0
  }
