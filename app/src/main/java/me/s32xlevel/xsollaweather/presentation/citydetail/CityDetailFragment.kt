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
import me.s32xlevel.xsollaweather.business.network.api
import me.s32xlevel.xsollaweather.business.network.asyncCall
import me.s32xlevel.xsollaweather.presentation.BaseFragment
import me.s32xlevel.xsollaweather.presentation.citychoose.CityChooseFragment
import me.s32xlevel.xsollaweather.presentation.util.CustomLinearDividerItemDecoration
import me.s32xlevel.xsollaweather.util.ErrorBannerManager.showErrorBanner
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.getIntFromPreferences

class CityDetailFragment : BaseFragment(R.layout.fragment_city_detail),
    SwipeRefreshLayout.OnRefreshListener, CityDetailView {

    companion object {
        fun newInstance(): Fragment {
            return CityDetailFragment()
        }
    }

    private val currentCityId: Int by lazy { context?.getIntFromPreferences(PreferencesManager.SAVED_CITY)!! }

    private lateinit var selectedDay: String

    private lateinit var presenter: CityDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CityDetailPresenter(this, cityRepository, weatherRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
        city_detail_layout.setOnRefreshListener(this)

        api.getForecast5day3hours(currentCityId)
            .enqueue(asyncCall(
                onSuccess = { onSuccessCall(it.body()!!) },
                onFailure = {
                    presenter.onFailureCall(
                        currentCityId = currentCityId,
                        onEmptyCache = { showErrorBanner { presenter.onErrorBannerBtnClickListener() } },
                        onNotEmptyCache = {
                            configureRecyclerForDates()
                            configureRecyclerForWeather()
                        }
                    )
                }
            ))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        api.getForecast5day3hours(currentCityId)
            .enqueue(asyncCall(
                onSuccess = { onSuccessCall(it.body()!!) },
                onFailure = {
                    presenter.onFailureCall(
                        currentCityId,
                        onEmptyCache = { showErrorBanner { presenter.onErrorBannerBtnClickListener() } })
                }
            ))

        city_detail_layout.isRefreshing = false
    }

    private fun onSuccessCall(weather: Weather) {
        presenter.onNeedUpdateCache(currentCityId, weather)
        presenter.onNeedUpdateSelectedDay(weatherRepository.findAllByCityId(currentCityId).weathers[0].dateTxt)
        configureRecyclerForDates()
        configureRecyclerForWeather()
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = presenter.onNeedCityName(currentCityId)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun configureRecyclerForDates() {
        with(dates_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = DatesAdapter(presenter.onNeedDates(currentCityId)).apply {
                setOnClickListener {
                    presenter.onNeedUpdateSelectedDay(it)
                    weather_rv.adapter = WeatherAdapter(presenter.onNeedGetWeatherForCurrentDay(currentCityId, selectedDay))
                }
            }
        }
    }

    private fun configureRecyclerForWeather() {
        with(weather_rv) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CustomLinearDividerItemDecoration(drawBeforeFirst = true, drawAfterLast = true))
            adapter = WeatherAdapter(presenter.onNeedGetWeatherForCurrentDay(currentCityId, selectedDay))
        }
    }

    override fun onRestartFragment() {
        changeFragment(newInstance(), cleanStack = true)
    }

    override fun onBackPressed() {
        changeFragment(CityChooseFragment.newInstance(), cleanStack = true)
    }

    override fun updateSelectedDay(newValue: String) {
        selectedDay = newValue
    }

    override fun onDetach() {
        presenter.detachView()
        super.onDetach()
    }
}
