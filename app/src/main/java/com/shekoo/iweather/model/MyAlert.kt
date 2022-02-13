package com.shekoo.iweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_list")
class MyAlert(
    @ColumnInfo(name = "first")
    var first : Long,
    @ColumnInfo(name = "last")
    var last : Long) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}