package me.s32xlevel.xsollaweather.presentation.addcity

interface AddCityView {
    fun goToBack()

    fun goToNextFragment()

    fun saveCityIdToPrefs(id: Int)

    fun saveCityIdToDb(id: Int)
}