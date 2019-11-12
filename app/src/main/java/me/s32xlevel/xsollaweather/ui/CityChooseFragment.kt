package me.s32xlevel.xsollaweather.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_city_choose.*
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.model.CityChoose
import me.s32xlevel.xsollaweather.network.api
import me.s32xlevel.xsollaweather.network.asyncCall
import me.s32xlevel.xsollaweather.ui.recyclers.CityChooseRecyclerAdapter
import me.s32xlevel.xsollaweather.ui.recyclers.GridSpacesItemDecoration
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment
import me.s32xlevel.xsollaweather.util.WeatherUtil

class CityChooseFragment : Fragment(R.layout.fragment_city_choose) {

    companion object {
        fun newInstance() = CityChooseFragment()
    }

    private val cities = arrayListOf<CityChoose>()

    private val cityRepository by lazy { App.getInstance().getDatabase().cityRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
        configureRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.city_choose_menu, menu)
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
    }

    private fun initData() {
        val allSaved = cityRepository.getAllSaved()
        allSaved.forEach { city ->
            api.getForecast5day3hours(city.id)
                .enqueue(asyncCall(
                    onSuccess = { weatherResponse ->
                        val weather = weatherResponse.body() ?: return@asyncCall
                        val tempMin = (weather.list[0].main.tempMin - 273).toInt()
                        val tempMax = (weather.list[0].main.tempMax - 273).toInt()
                        val icon = WeatherUtil.getWeatherImageResourceFromDescription(weather.list[0].weatherDescriptions[0].description) ?: return@asyncCall
                        val cityChoose = CityChoose(
                            city.id,
                            city.name,
                            BitmapFactory.decodeResource(resources, icon),
                            tempMin,
                            tempMax
                        )
                        cities.add(cityChoose)
                    },
                    onBadRequest = {}
                ))
        }
    }

    private fun configureRecycler() {
        val adapter = CityChooseRecyclerAdapter(cities)
        adapter.setOnCityClickListener { activity?.changeFragment(CityDetailFragment.newInstance(it)) }
        cities_rv.layoutManager = GridLayoutManager(context, 2)
        cities_rv.addItemDecoration(GridSpacesItemDecoration(8))
        cities_rv.adapter = adapter
        cities_rv.requestLayout()
    }
}
