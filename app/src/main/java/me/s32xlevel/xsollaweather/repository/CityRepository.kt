package me.s32xlevel.xsollaweather.repository

interface CityRepository {
    fun get(name: String)

    fun getAll()

    fun delete()

    fun save()
}