package me.s32xlevel.xsollaweather.presentation.citychoose

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_city_choose.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.business.model.CityChoose
import me.s32xlevel.xsollaweather.business.network.api
import me.s32xlevel.xsollaweather.business.network.asyncCall
import me.s32xlevel.xsollaweather.presentation.BaseFragment
import me.s32xlevel.xsollaweather.presentation.addcity.AddCityFragment
import me.s32xlevel.xsollaweather.presentation.citydetail.CityDetailFragment
import me.s32xlevel.xsollaweather.presentation.util.GridSpacesItemDecoration
import me.s32xlevel.xsollaweather.util.ErrorBannerManager.showErrorBanner
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.setToPreferences

class CityChooseFragment : BaseFragment(R.layout.fragment_city_choose),
    SwipeRefreshLayout.OnRefreshListener, CityChooseView {

    companion object {
        fun newInstance() = CityChooseFragment()
    }

    private lateinit var presenter: CityChoosePresenter

    private val adaptersList = arrayListOf<CityChoose>()

    private val recyclerAdapter = CityChooseAdapter(adaptersList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CityChoosePresenter(this, cityRepository, weatherRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        city_choose_layout.setOnRefreshListener(this)
        configureToolbar()
    }

    override fun onResume() {
        super.onResume()
        if (adaptersList.isNotEmpty()) adaptersList.clear()
        initDataAndConfigureCityChooseAdapter()
        configureRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.city_choose_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_add -> {
                changeFragment(AddCityFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
    }

    override fun onRefresh() {
        initDataAndConfigureCityChooseAdapter()
        configureRecycler()
        city_choose_layout.isRefreshing = false
    }

    private fun initDataAndConfigureCityChooseAdapter() {
        presenter.showExtraText(false)
        val savedCities = cityRepository.getAllSaved()

        if (savedCities.isEmpty()) {
            presenter.showExtraText(true)
        } else {
            savedCities.forEach { city ->
                val cachedWeather = weatherRepository.findAllByCityId(city.id).weathers
                api.getForecast5day3hours(city.id)
                    .enqueue(asyncCall(
                        onSuccess = { weatherResponse ->
                            presenter.onSuccessCall(adaptersList, city, cachedWeather, weatherResponse.body()!!)
                            recyclerAdapter.notifyDataSetChanged()
                        },
                        onFailure = {
                            if (cachedWeather.isEmpty()) {
                                showErrorBanner { presenter.onErrorBannerBtnClickListener() }
                            } else {
                                presenter.onFailureCall(adaptersList, city, cachedWeather)
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
                setOnCityClickListener { presenter.onCityClickListener(it) }
                setOnCityLongClickListener {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.fragment_choose_delete_city, it.name))
                        .setPositiveButton(getString(R.string.fragment_choose_yes)) { _, _ ->
                            presenter.onCityLongClickListener(it)
                            notifyDataSetChanged()
                        }
                        .setNegativeButton(getString(R.string.fragment_choose_no)) { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }
    }

    override fun goToNextFragment() {
        changeFragment(CityDetailFragment.newInstance())
    }

    override fun saveCityIdToPrefs(id: Int) {
        context?.setToPreferences(PreferencesManager.SAVED_CITY, id)
    }

    override fun clearCityFromPrefs() {
        context?.setToPreferences(PreferencesManager.SAVED_CITY, PreferencesManager.EMPTY_VALUE)
    }

    override fun addToAdaptersList(cityChoose: CityChoose) {
        adaptersList.add(cityChoose)
    }

    override fun clearFromAdaptersList(cityChoose: CityChoose) {
        adaptersList.remove(cityChoose)
    }

    override fun showExtraText(show: Boolean) {
        if (show) cities_empty_tv.visibility = View.VISIBLE
        else cities_empty_tv.visibility = View.GONE
    }

    override fun onRestartFragment() {
        changeFragment(newInstance(), cleanStack = true)
    }

    override fun onDetach() {
        presenter.detachView()
        super.onDetach()
    }
}
