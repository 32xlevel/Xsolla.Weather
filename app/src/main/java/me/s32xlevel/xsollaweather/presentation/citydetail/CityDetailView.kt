package me.s32xlevel.xsollaweather.presentation.citydetail

interface CityDetailView {

    fun onRestartFragment()

    fun onBackPressed()

    fun updateSelectedDay(newValue: String)

}