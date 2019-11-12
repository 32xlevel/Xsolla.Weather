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

    @Query("UPDATE cities SET is_saved = 0 WHERE id = :cityId")
    fun delete(cityId: Int)

    // TODO: Когда выполнилось, нужно сделать обновление на CityChooseFragment
    @Query("UPDATE cities SET is_saved = 1 WHERE id = :cityId")
    fun save(cityId: Int)
}