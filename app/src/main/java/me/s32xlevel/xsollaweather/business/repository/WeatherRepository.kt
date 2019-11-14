package me.s32xlevel.xsollaweather.business.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.s32xlevel.xsollaweather.business.model.CityWithWeather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity

@Dao
interface WeatherRepository {
    @Query("SELECT * FROM cities WHERE id = :cityId")
    fun findAllByCityId(cityId: Int): CityWithWeather

    @Query("DELETE FROM weather")
    fun clear()

    @Insert
    fun save(weather: WeatherEntity)
}