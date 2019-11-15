package me.s32xlevel.xsollaweather.util

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.business.model.Weather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import java.util.*

object DbUtils {
    fun populateDb(db: SupportSQLiteDatabase) {
        db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
            put("id", 524901)
            put("name", "Москва")
            put("is_saved", true)
        })
        db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
            put("id", 511180)
            put("name", "Пермь")
            put("is_saved", true)
        })
        db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
            put("id", 498817)
            put("name", "Санкт-Петербург")
            put("is_saved", false)
        })
        db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
            put("id", 1496745)
            put("name", "Новосибирск")
            put("is_saved", false)
        })
    }

    fun saveWeatherToDb(pojoWeather: Weather) {
        val cityId = pojoWeather.city.id
        pojoWeather.list.forEach { weatherListItem ->
            val description = weatherListItem.weatherDescriptions[0].description
            val dateTxt = weatherListItem.dtTxt
            val temp = weatherListItem.main.temp
            val weatherEntity =
                WeatherEntity(cityId = cityId, description = description, dateTxt = dateTxt, temp = temp, id = UUID.randomUUID().toString())
            App.getInstance().getDatabase().weatherRepository().save(weatherEntity)
        }
    }
}