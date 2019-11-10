package me.s32xlevel.xsollaweather.repository

import me.s32xlevel.xsollaweather.model.Weather

interface WeatherRepository {
    fun getActual(cityName: String)

    fun getCached(cityName: String)

    fun clearCache()

    fun cacheData(weather: Weather)

}