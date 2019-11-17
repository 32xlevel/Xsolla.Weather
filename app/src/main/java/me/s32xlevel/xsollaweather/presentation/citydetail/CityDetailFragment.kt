package me.s32xlevel.xsollaweather.presentation.citydetail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_city_detail.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.business.model.Weather
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import me.s32xlevel.xsollaweather.business.network.api
import me.s32xlevel.xsollaweather.business.network.asyncCall
import me.s32xlevel.xsollaweather.presentation.BaseFragment
import me.s32xlevel.xsollaweather.presentation.citychoose.CityChooseFragment
import me.s32xlevel.xsollaweather.presentation.util.CustomLinearDividerItemDecoration
import me.s32xlevel.xsollaweather.util.DbUtils
import me.s32xlevel.xsollaweather.util.ErrorBannerManager.showErrorBanner
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.getIntFromPreferences

class CityDetailFragment : BaseFragment(R.layout.fragment_city_detail),
    SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        api.getForecast5day3hours(currentCityId)
            .enqueue(asyncCall(
                onSuccess = {
                    onSuccessCall(it.body()!!)
                },
                onFailure = {
                    if (weatherRepository.findAllByCityId(currentCityId).weathers.isEmpty()) {
                        showErrorBanner {
                            changeFragment(newInstance(), cleanStack = true)
                        }
                    }
                }
            ))

        city_detail_layout.isRefreshing = false
    }

    companion object {
        fun newInstance(): Fragment {
            return CityDetailFragment()
        }
    }

    private val currentCityId: Int by lazy { context?.getIntFromPreferences(PreferencesManager.SAVED_CITY)!! }

    private lateinit var selectedDay: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
        city_detail_layout.setOnRefreshListener(this)

        api.getForecast5day3hours(currentCityId)
            .enqueue(asyncCall(
                onSuccess = {
                    onSuccessCall(it.body()!!)
                },
                onFailure = {
                    val cityWeatherInDb = weatherRepository.findAllByCityId(currentCityId)
                    if (cityWeatherInDb.weathers.isEmpty()) {
                        showErrorBanner {
                            changeFragment(newInstance(), cleanStack = true)
                        }
                    } else {
                        selectedDay =
                            weatherRepository.findAllByCityId(currentCityId).weathers[0].dateTxt
                        configureRecyclerForDates()
                        configureRecyclerForWeather()
                    }
                }
            ))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.changeFragment(CityChooseFragment.newInstance(), cleanStack = true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onSuccessCall(weather: Weather) {
        weatherRepository.clearById(currentCityId)
        DbUtils.saveWeatherToDb(weather)
        selectedDay =
            weatherRepository.findAllByCityId(currentCityId).weathers[0].dateTxt
        configureRecyclerForDates()
        configureRecyclerForWeather()
    }

    private fun configureToolbar() {
        val city = cityRepository.findById(currentCityId)
        with((activity as AppCompatActivity).supportActionBar!!) {
            title = city.name
            setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    private fun configureRecyclerForDates() {
        val weathers = weatherRepository.findAllByCityId(currentCityId).weathers
        val set = mutableSetOf<String>()
        for (weather in weathers) {
            set.add(weather.dateTxt.split(" ")[0])
        }

        with(dates_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = DatesAdapter(set).apply {
                setOnClickListener {
                    selectedDay = it
                    weather_rv.adapter =
                        WeatherAdapter(
                            getWeatherByCurrentDate()
                        )
                }
            }
        }
    }

    private fun configureRecyclerForWeather() {
        weather_rv.layoutManager = LinearLayoutManager(context)
        weather_rv.addItemDecoration(
            CustomLinearDividerItemDecoration(
                drawBeforeFirst = true,
                drawAfterLast = true
            )
        )
        weather_rv.adapter = WeatherAdapter(
            getWeatherByCurrentDate()
        )
    }

    private fun getWeatherByCurrentDate(): List<WeatherEntity> {
        val weatherList = weatherRepository.findAllByCityId(currentCityId).weathers
        val date = selectedDay.split(" ")[0]

        val list = mutableListOf<WeatherEntity>()

        for (weatherEntity in weatherList) {
            if (weatherEntity.dateTxt.contains(date)) {
                list.add(weatherEntity)
            }
        }

        return list
    }
}
