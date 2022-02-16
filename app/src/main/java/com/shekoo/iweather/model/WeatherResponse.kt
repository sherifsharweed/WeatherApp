package com.shekoo.iweather.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.shekoo.iweather.model.Alerts
import com.shekoo.iweather.model.Current
import com.shekoo.iweather.model.Hourly


@Entity(tableName = "weatherresponse_table")
 data class WeatherResponse (
	@PrimaryKey(autoGenerate = true)
	var id : Int = 0,
	@SerializedName("lat") val lat : Double,
	@SerializedName("lon") val lon : Double,
	@SerializedName("timezone") val timezone : String,
	@SerializedName("timezone_offset") val timezone_offset : Int,
	@SerializedName("current")
	@Embedded
	val current : Current,
	@SerializedName("hourly") val hourly : List<Hourly>,
	@SerializedName("daily") val daily : List<Daily>,
	@SerializedName("alerts") val alerts : List<Alerts>
)