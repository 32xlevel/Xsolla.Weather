package me.s32xlevel.xsollaweather.presentation.citychoose

import me.s32xlevel.xsollaweather.business.model.CityChoose

interface CityChooseView {

    fun goToNextFragment()

    fun saveCityIdToPrefs(id: Int)

    fun addToAdaptersList(cityChoose: CityChoose)

    fun clearFromAdaptersList(cityChoose: CityChoose)

    fun clearCityFromPrefs()

    fun showExtraText(show: Boolean)

    fun onRestartFragment()
}