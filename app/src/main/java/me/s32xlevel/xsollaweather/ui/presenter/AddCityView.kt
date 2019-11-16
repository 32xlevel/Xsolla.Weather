package me.s32xlevel.xsollaweather.ui.presenter

interface AddCityView {
    fun goToBack()

    fun goToNextFragment()

    fun saveCityIdToPrefs(id: Int)

    fun saveCityIdToDb(id: Int)
}