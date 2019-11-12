package me.s32xlevel.xsollaweather.network

import me.s32xlevel.xsollaweather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    fun getForecast5day3hours(@Query("id") cityId: Int, @Query("appid") appId: String = API_KEY): Call<Weather>

}