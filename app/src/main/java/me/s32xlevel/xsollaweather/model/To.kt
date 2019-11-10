package me.s32xlevel.xsollaweather.model

import android.graphics.Bitmap

data class CityChoose(
    val name: String,
    val weatherImage: Bitmap,
    val tempMin: Double,
    val tempMax: Double
)