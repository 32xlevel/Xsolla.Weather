package me.s32xlevel.xsollaweather

import androidx.room.Database
import androidx.room.RoomDatabase
import me.s32xlevel.xsollaweather.model.CityEntity
import me.s32xlevel.xsollaweather.repository.CityRepository

@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityRepository(): CityRepository
}