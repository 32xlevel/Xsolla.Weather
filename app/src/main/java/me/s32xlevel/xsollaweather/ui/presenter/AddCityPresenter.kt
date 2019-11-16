package me.s32xlevel.xsollaweather.ui.presenter

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