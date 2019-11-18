package me.s32xlevel.xsollaweather.presentation.addcity

import android.text.Editable
import me.s32xlevel.xsollaweather.business.repository.CityRepository

class AddCityPresenter(
    private var addCityView: AddCityView?,
    private val cityRepository: CityRepository
) {

    fun onFindCity(query: Editable?) {
        if (query == null || query.isBlank()) {
            addCityView?.updateAdapterData(cityRepository.getAllNotSaved())
        } else {
            addCityView?.updateAdapterData(cityRepository.getAllNotSaved().filter { it.name.contains(query, ignoreCase = true) })
        }
    }

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