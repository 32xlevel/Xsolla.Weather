package me.s32xlevel.xsollaweather.model

import android.graphics.Bitmap

data class CityChoose(
    val id: Int,
    val name: String,
    val weatherImage: Bitmap,
    val tempMin: Int,
    val tempMax: Int
)

data class WeatherForDate(
    val time: String,
    val weatherImage: Bitmap,
    val temp: Double
)