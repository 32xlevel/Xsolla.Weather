package me.s32xlevel.xsollaweather.util

import me.s32xlevel.xsollaweather.R
import java.text.SimpleDateFormat
import java.util.*

object WeatherUtil {

    fun getDayOfWeek(fullDate: String): String {
        return when (getDayOfWeekInt(fullDate)) {
            1 -> "Вс"
            2 -> "Пн"
            3 -> "Вт"
            4 -> "Ср"
            5 -> "Чт"
            6 -> "Пт"
            7 -> "Сб"
            else -> throw IllegalStateException()
        }
    }

    private fun getDayOfWeekInt(fullDate: String) =
        Calendar.getInstance().apply { time = SimpleDateFormat("yyyy-MM-dd").parse(fullDate) }
            .get(Calendar.DAY_OF_WEEK)


    fun getWeatherImageResourceFromDescription(weatherDescription: String): Int {
        return when {
            weatherDescription.contains("cloud", ignoreCase = true) -> R.drawable.cloudly
            weatherDescription.contains("rain", ignoreCase = true) -> R.drawable.rainly
            weatherDescription.contains("snow", ignoreCase = true) -> R.drawable.snow
            weatherDescription.contains("storm", ignoreCase = true) -> R.drawable.storm
            weatherDescription.contains("sun", ignoreCase = true) -> R.drawable.sun
            weatherDescription.contains("wind", ignoreCase = true) -> R.drawable.windy
            weatherDescription.contains("sky", ignoreCase = true) -> R.drawable.sun // TODO
            else -> throw IllegalStateException()
        }
    }
}