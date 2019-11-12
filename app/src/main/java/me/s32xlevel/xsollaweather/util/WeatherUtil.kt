package me.s32xlevel.xsollaweather.util

import me.s32xlevel.xsollaweather.R

object WeatherUtil {
    fun getWeatherImageResourceFromDescription(weatherDescription: String): Int? {
        return when {
            weatherDescription.contains("cloud", ignoreCase = true) -> R.drawable.cloudly
            weatherDescription.contains("rain", ignoreCase = true) -> R.drawable.rainly
            weatherDescription.contains("snow", ignoreCase = true) -> R.drawable.snow
            weatherDescription.contains("storm", ignoreCase = true) -> R.drawable.storm
            weatherDescription.contains("sun", ignoreCase = true) -> R.drawable.sun
            weatherDescription.contains("wind", ignoreCase = true) -> R.drawable.windy
            else -> null
        }
    }
}