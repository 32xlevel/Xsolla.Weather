package me.s32xlevel.xsollaweather.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_city_choose.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.business.model.CityChoose
import me.s32xlevel.xsollaweather.business.network.api
import me.s32xlevel.xsollaweather.business.network.asyncCall
import me.s32xlevel.xsollaweather.ui.recyclers.CityChooseAdapter
import me.s32xlevel.xsollaweather.ui.recyclers.GridSpacesItemDecoration
import me.s32xlevel.xsollaweather.util.DbUtils
import me.s32xlevel.xsollaweather.util.ErrorBannerManager.showErrorBanner
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.setToPreferences
import me.s32xlevel.xsollaweather.util.WeatherUtil

class CityChooseFragment : BaseFragment(R.layout.fragment_city_choose) {

    companion object {
        fun newInstance() = CityChooseFragment()
    }

    private val cities = arrayListOf<CityChoose>()

    private val recyclerAdapter = CityChooseAdapter(cities)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
    }

    override fun onResume() {
        super.onResume()
        if (cities.isNotEmpty()) {
            cities.clear()
        }

        initDataAndConfigureCityChooseAdapter()
        configureRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.city_choose_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_add -> {
                changeFragment(AddCityFragment.newInstance())
                return true
            }
            R.id.app_bar_update -> {
                changeFragment(newInstance(), cleanStack = true) // TODO ?
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
    }

    private fun initDataAndConfigureCityChooseAdapter() {
        cities_empty_tv.visibility = View.GONE
        val savedCities = cityRepository.getAllSaved()

        if (savedCities.isEmpty()) {
            cities_empty_tv.visibility = View.VISIBLE
        } else {
            savedCities.forEach { city ->
                val weatherForCity = weatherRepository.findAllByCityId(city.id).weathers
                api.getForecast5day3hours(city.id)
                    .enqueue(asyncCall(
                        onSuccess = { weatherResponse ->
                            if (weatherForCity.isNotEmpty()) {
                                weatherRepository.clearById(city.id)
                            }

                            val weather = weatherResponse.body() ?: return@asyncCall
                            val iconRes =
                                WeatherUtil.getWeatherImageResourceFromDescription(weather.list[0].weatherDescriptions[0].description)

                            cities.add(
                                CityChoose(
                                    id = city.id,
                                    name = city.name,
                                    weatherImage = BitmapFactory.decodeResource(
                                        resources,
                                        iconRes
                                    ),
                                    tempMin = (weather.list[0].main.tempMin - 273).toInt(),
                                    tempMax = (weather.list[0].main.tempMax - 273).toInt()
                                )
                            )
                            DbUtils.saveWeatherToDb(weather)
                            recyclerAdapter.notifyDataSetChanged()
                        },
                        onFailure = {
                            if (weatherForCity.isEmpty()) {
                                showErrorBanner {
                                    changeFragment(newInstance(), cleanStack = true)
                                }
                            } else {
                                val iconRes =
                                    WeatherUtil.getWeatherImageResourceFromDescription(weatherForCity[0].description)
                                val cityChoose = CityChoose(
                                    id = city.id,
                                    name = city.name,
                                    weatherImage = BitmapFactory.decodeResource(
                                        resources,
                                        iconRes
                                    ),
                                    tempMin = (weatherForCity[0].temp - 274).toInt(),
                                    tempMax = (weatherForCity[0].temp - 273).toInt()
                                )

                                if (cityChoose !in cities) {
                                    cities.add(cityChoose)
                                }

                                recyclerAdapter.notifyDataSetChanged()
                            }
                        }
                    ))
            }
        }
    }

    private fun configureRecycler() {
        with(cities_rv) {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacesItemDecoration(8))
            adapter = recyclerAdapter.apply {
                setOnCityClickListener { cityId ->
                    context?.setToPreferences(PreferencesManager.SAVED_CITY, cityId)
                    changeFragment(CityDetailFragment.newInstance())
                }
                setOnCityLongClickListener {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.fragment_choose_delete_city))
                        .setPositiveButton(getString(R.string.fragment_choose_yes)) { _, _ ->
                            cityRepository.delete(it.id)
                            cities.remove(it)

                            if (cityRepository.getAllSaved().isEmpty()) {
                                cities_empty_tv.visibility = View.VISIBLE
                                context?.setToPreferences(PreferencesManager.SAVED_CITY, -1)
                            }

                            notifyDataSetChanged()
                        }
                        .setNegativeButton(getString(R.string.fragment_choose_no)) { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }
    }
}
