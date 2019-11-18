package me.s32xlevel.xsollaweather.presentation.addcity

import me.s32xlevel.xsollaweather.business.model.CityEntity

interface AddCityView {
    fun goToBack()

    fun goToNextFragment()

    fun saveCityIdToPrefs(id: Int)

    fun saveCityIdToDb(id: Int)

    fun updateAdapterData(data: List<CityEntity>)
}