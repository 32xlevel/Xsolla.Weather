package me.s32xlevel.xsollaweather

import androidx.room.Database
import androidx.room.RoomDatabase
import me.s32xlevel.xsollaweather.model.CityEntity
import me.s32xlevel.xsollaweather.model.CityWithWeather
import me.s32xlevel.xsollaweather.model.WeatherEntity
import me.s32xlevel.xsollaweather.repository.CityRepository
import me.s32xlevel.xsollaweather.repository.WeatherRepository

@Database(entities = [CityEntity::class, WeatherEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityRepository(): CityRepository
    abstract fun weatherRepository(): WeatherRepository
}