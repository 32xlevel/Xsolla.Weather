package me.s32xlevel.xsollaweather.business.model

import android.graphics.Bitmap

data class CityChoose(
    val id: Int,
    val name: String,
    val weatherImage: Int,
    val tempMin: Int,
    val tempMax: Int
)