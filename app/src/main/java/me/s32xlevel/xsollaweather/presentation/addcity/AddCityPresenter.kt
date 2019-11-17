package me.s32xlevel.xsollaweather.presentation.addcity

class AddCityPresenter(private var addCityView: AddCityView?) {

    fun onBackPressed() {
        addCityView?.goToBack()
    }

    fun onCityClickListener(cityId: Int) {
        addCityView?.saveCityIdToPrefs(cityId)
        addCityView?.saveCityIdToDb(cityId)
        addCityView?.goToNextFragment()
    }

    fun onDetachView() {
        addCityView = null
    }

}