package me.s32xlevel.xsollaweather.presentation.citydetail

import me.s32xlevel.xsollaweather.business.model.Weather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import me.s32xlevel.xsollaweather.business.repository.CityRepository
import me.s32xlevel.xsollaweather.business.repository.WeatherRepository
import me.s32xlevel.xsollaweather.util.DbUtils

class CityDetailPresenter(
    private var cityDetailView: CityDetailView?,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) {

    fun onFailureCall(currentCityId: Int, onEmptyCache: () -> Unit, onNotEmptyCache: () -> Unit = {}) {
        val cachedWeather = weatherRepository.findAllByCityId(currentCityId)
        if (cachedWeather.weathers.isEmpty()) {
            onEmptyCache.invoke()
        } else {
            onNeedUpdateSelectedDay(cachedWeather.weathers[0].dateTxt)
            onNotEmptyCache.invoke()
        }
    }

    fun onErrorBannerBtnClickListener() {
        cityDetailView?.onRestartFragment()
    }

    fun onBackPressed() {
        cityDetailView?.onBackPressed()
    }

    fun onNeedUpdateCache(currentCityId: Int, newWeather: Weather) {
        weatherRepository.clearById(currentCityId)
        DbUtils.saveWeatherToDb(newWeather)
    }

    fun onNeedUpdateSelectedDay(newValue: String) {
        cityDetailView?.updateSelectedDay(newValue)
    }

    fun onNeedDates(currentCityId: Int): Set<String> {
        val weathers = weatherRepository.findAllByCityId(currentCityId).weathers
        val set = mutableSetOf<String>()
        for (weather in weathers) {
            set.add(weather.dateTxt.split(" ")[0])
        }
        return set
    }

    fun onNeedCityName(currentCityId: Int) = cityRepository.findById(currentCityId).name

    fun onNeedGetWeatherForCurrentDay(currentCityId: Int, selectedDay: String): List<WeatherEntity> {
        val weatherList = weatherRepository.findAllByCityId(currentCityId).weathers
        val date = selectedDay.split(" ")[0]

        val list = mutableListOf<WeatherEntity>()

        for (weatherEntity in weatherList) {
            if (weatherEntity.dateTxt.contains(date)) {
                list.add(weatherEntity)
            }
        }

        return list
    }

    fun detachView() {
        cityDetailView = null
    }
}