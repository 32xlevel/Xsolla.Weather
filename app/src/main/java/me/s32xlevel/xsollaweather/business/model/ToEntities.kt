package me.s32xlevel.xsollaweather.business.model

data class CityChoose(
    val id: Int,
    val name: String,
    val weatherImage: Int,
    val tempMin: Int,
    val tempMax: Int
)