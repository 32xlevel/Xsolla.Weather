package me.s32xlevel.xsollaweather.util

import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.business.model.Weather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import java.util.*

object DbUtils {
    fun saveWeatherToDb(pojoWeather: Weather) {
        val cityId = pojoWeather.city.id
        pojoWeather.list.forEach { weatherListItem ->
            val description = weatherListItem.weatherDescriptions[0].description
            val dateTxt = weatherListItem.dtTxt
            val temp = weatherListItem.main.temp
            val weatherEntity =
                WeatherEntity(cityId = cityId, description = description, dateTxt = dateTxt, temp = temp, id = UUID.randomUUID().toString())
            App.getInstance().getDatabase().weatherRepository().save(weatherEntity)
        }
    }
}