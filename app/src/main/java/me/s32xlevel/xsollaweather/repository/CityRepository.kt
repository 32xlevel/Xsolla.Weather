package me.s32xlevel.xsollaweather.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.s32xlevel.xsollaweather.model.CityEntity

@Dao
interface CityRepository {

    @Query("SELECT * FROM cities")
    fun getAllList(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE is_saved = 1")
    fun getAllSaved(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE is_saved = 0")
    fun getAllNotSaved(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE name = :name")
    fun findByName(name: String): CityEntity

    @Query("DELETE FROM cities WHERE id = :id")
    fun delete(id: Int)

    @Query("UPDATE cities SET is_saved = 1 WHERE id = :cityId")
    fun save(cityId: Int)
}