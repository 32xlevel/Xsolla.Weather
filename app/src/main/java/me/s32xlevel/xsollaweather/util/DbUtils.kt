package me.s32xlevel.xsollaweather.util

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.business.model.Weather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import java.util.*

object DbUtils {
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

    // TODO: Смена реализации для поиска по всем возможным городам
    fun populateDb(db: SupportSQLiteDatabase) {
        insertCity(db, 524901, "Москва", true)
        insertCity(db, 511180, "Пермь", true)
        insertCity(db, 498817, "Санкт-Петербург", false)
        insertCity(db, 1496745, "Новосибирск", false)
        insertCity(db, 1486209, "Екатеринбург", false)
        insertCity(db, 1508290, "Челябинск", false)
        insertCity(db, 499099, "Самара", false)
        insertCity(db, 480041, "Тверь", false)
        insertCity(db, 486137, "Сургут", false)
        insertCity(db, 551487, "Казань", false)
        insertCity(db, 498671, "Саратов", false)
        insertCity(db, 472755, "Волгоград", false)
        insertCity(db, 472039, "Воронеж", false)
        insertCity(db, 703448, "Киев", false)
        insertCity(db, 625144, "Минск", false)
        insertCity(db, 456172, "Рига", false)
        insertCity(db, 2988507, "Париж", false)
        insertCity(db, 3882428, "Лос-Анджелес", false)
        insertCity(db, 1699806, "Мехико", false)
        insertCity(db, 4684888, "Даллас", false)
        insertCity(db, 4887398, "Чикаго", false)
        insertCity(db, 5128638, "Нью-Йорк", false)
    }

    private fun insertCity(db: SupportSQLiteDatabase, id: Int, name: String, isSaved: Boolean) {
        db.insert("cities", SQLiteDatabase.CONFLICT_NONE, ContentValues().apply {
            put("id", id)
            put("name", name)
            put("is_saved", isSaved)
        })
    }
}