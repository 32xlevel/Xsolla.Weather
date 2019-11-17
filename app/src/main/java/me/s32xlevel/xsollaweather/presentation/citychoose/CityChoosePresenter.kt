package me.s32xlevel.xsollaweather.presentation.citychoose

import me.s32xlevel.xsollaweather.business.model.CityChoose
import me.s32xlevel.xsollaweather.business.model.CityEntity
import me.s32xlevel.xsollaweather.business.model.Weather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import me.s32xlevel.xsollaweather.business.repository.CityRepository
import me.s32xlevel.xsollaweather.business.repository.WeatherRepository
import me.s32xlevel.xsollaweather.util.DbUtils
import me.s32xlevel.xsollaweather.util.WeatherUtil.getWeatherImageResourceFromDescription

class CityChoosePresenter(
    private var cityChooseView: CityChooseView?,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) {

    fun onSuccessCall(citiesList: List<CityChoose>, city: CityEntity, cachedWeather: List<WeatherEntity>, newWeather: Weather) {
        if (cachedWeather.isNotEmpty()) {
            weatherRepository.clearById(city.id)
        }

        val iconRes = getWeatherImageResourceFromDescription(newWeather.list[0].weatherDescriptions[0].description)

        val cityChoose = CityChoose(
            id = city.id,
            name = city.name,
            weatherImage = iconRes,
            tempMin = (newWeather.list[0].main.tempMin - 273).toInt(),
            tempMax = (newWeather.list[0].main.tempMax - 273).toInt()
        )

        if (cityChoose !in citiesList) {
            cityChooseView?.addToAdaptersList(cityChoose)
        }

        DbUtils.saveWeatherToDb(newWeather)
    }

    fun onFailureCall(citiesList: List<CityChoose>, city: CityEntity, cachedWeather: List<WeatherEntity>) {
        val iconRes = getWeatherImageResourceFromDescription(cachedWeather[0].description)

        val cityChoose = CityChoose(
            id = city.id,
            name = city.name,
            weatherImage = iconRes,
            tempMin = (cachedWeather[0].temp - 273).toInt(),
            tempMax = (cachedWeather[0].temp - 273).toInt()
        )

        if (citiesList.find { it.id == cityChoose.id } == null) {
            cityChooseView?.addToAdaptersList(cityChoose)
        }
    }

    fun onErrorBannerBtnClickListener() {
        cityChooseView?.onRestartFragment()
    }

    fun onCityClickListener(id: Int) {
        cityChooseView?.saveCityIdToPrefs(id)
        cityChooseView?.goToNextFragment()
    }

    fun onCityLongClickListener(cityChoose: CityChoose) {
        cityRepository.delete(cityChoose.id)
        cityChooseView?.clearFromAdaptersList(cityChoose)

        if (cityRepository.getAllSaved().isEmpty()) {
            showExtraText(true)
            cityChooseView?.clearCityFromPrefs()
        }
    }

    fun showExtraText(show: Boolean) {
        cityChooseView?.showExtraText(show)
    }

    fun detachView() {
        cityChooseView = null
    }
}