package me.s32xlevel.xsollaweather.business.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: Int,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<WeatherList>
)

data class City(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("timezone")
    val timezone: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("coord")
    val coord: Coord
)

data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

data class WeatherList(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: WeatherMain,
    @SerializedName("weather")
    val weatherDescriptions: List<WeatherDescription>,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("sys")
    val sys: Sys
)

data class WeatherMain(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Double
)

data class WeatherDescription(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Clouds(
    @SerializedName("all")
    val all: Int
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int
)

data class Rain(
    @SerializedName("3h")
    val h3: Double
)

data class Sys(
    @SerializedName("pod")
    val pod: String
)