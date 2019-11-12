package me.s32xlevel.xsollaweather.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.s32xlevel.xsollaweather.model.CityWithWeather
import me.s32xlevel.xsollaweather.model.WeatherEntity

@Dao
interface WeatherRepository {
    @Query("SELECT * FROM cities WHERE id = :cityId")
    fun findAllByCityId(cityId: Int): CityWithWeather

    @Query("DELETE FROM weather")
    fun clear()

    @Insert
    fun save(weather: WeatherEntity)
}