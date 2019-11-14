package me.s32xlevel.xsollaweather.business.repository

import androidx.room.Dao
import androidx.room.Query
import me.s32xlevel.xsollaweather.business.model.CityEntity

@Dao
interface CityRepository {

    @Query("SELECT * FROM cities WHERE is_saved = 1")
    fun getAllSaved(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE is_saved = 0")
    fun getAllNotSaved(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE id = :id")
    fun findById(id: Int): CityEntity

    @Query("UPDATE cities SET is_saved = 0 WHERE id = :cityId")
    fun delete(cityId: Int)

    @Query("UPDATE cities SET is_saved = 1 WHERE id = :cityId")
    fun save(cityId: Int)
}