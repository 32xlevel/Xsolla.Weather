package me.s32xlevel.xsollaweather.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_city_choose.*
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.model.CityChoose
import me.s32xlevel.xsollaweather.model.Weather
import me.s32xlevel.xsollaweather.model.WeatherEntity
import me.s32xlevel.xsollaweather.network.api
import me.s32xlevel.xsollaweather.network.asyncCall
import me.s32xlevel.xsollaweather.ui.recyclers.CityChooseRecyclerAdapter
import me.s32xlevel.xsollaweather.ui.recyclers.GridSpacesItemDecoration
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment
import me.s32xlevel.xsollaweather.util.WeatherUtil
import java.util.*

class CityChooseFragment : Fragment(R.layout.fragment_city_choose) {

    companion object {
        fun newInstance() = CityChooseFragment()
    }

    private val cities = arrayListOf<CityChoose>()

    private val cityRepository by lazy { App.getInstance().getDatabase().cityRepository() }

    private val weatherRepository by lazy { App.getInstance().getDatabase().weatherRepository() }

    private val recyclerAdapter = CityChooseRecyclerAdapter(cities)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.supportFragmentManager?.popBackStack()
                return true
            }
            R.id.app_bar_add -> {
                activity?.changeFragment(AddCityFragment.newInstance())
                return true
            }
            R.id.app_bar_update -> {
                activity?.changeFragment(newInstance(), cleanStack = true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        with((activity as AppCompatActivity).supportActionBar!!) {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false) // TODO: В зависимости от того на каком экране
        }
        setHasOptionsMenu(true)
    }

    private fun initData() {
        val allSaved = cityRepository.getAllSaved()
        allSaved.forEach { city ->
            api.getForecast5day3hours(city.id)
                .enqueue(asyncCall(
                    onSuccess = { weatherResponse ->
                        requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                            .edit()
                            .putLong("LastNetworkConnect", System.currentTimeMillis())
                            .apply()

                        val weather = weatherResponse.body() ?: return@asyncCall
                        val iconRes =
                            WeatherUtil.getWeatherImageResourceFromDescription(weather.list[0].weatherDescriptions[0].description)
                                ?: return@asyncCall
                        cities.add(
                            CityChoose(
                                id = city.id,
                                name = city.name,
                                weatherImage = BitmapFactory.decodeResource(resources, iconRes),
                                tempMin = (weather.list[0].main.tempMin - 273).toInt(),
                                tempMax = (weather.list[0].main.tempMax - 273).toInt()
                            )
                        )
                        saveWeatherToDb(weather)
                        recyclerAdapter.notifyDataSetChanged()
                    },
                    onBadRequest = {}
                ))
        }
    }

    private fun configureRecycler() {
        with(cities_rv) {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacesItemDecoration(8))
            adapter = recyclerAdapter.apply {
                setOnCityClickListener { cityId ->
                    requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putInt("SavedCity", cityId)
                        .apply()
                    activity?.changeFragment(CityDetailFragment.newInstance())
                }
                setOnCityLongClickListener {
                    AlertDialog.Builder(context)
                        .setTitle("Удалить город?")
                        .setPositiveButton("Да") { dialog, which ->
                            cityRepository.delete(it.id)
                            cities.remove(it)
                            notifyDataSetChanged()
                        }
                        .setNegativeButton("Нет") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    private fun saveWeatherToDb(pojoWeather: Weather) {
        val cityId = pojoWeather.city.id
        pojoWeather.list.forEachIndexed { index, weatherListItem ->
            val description = weatherListItem.weatherDescriptions[0].description
            val dateTxt = weatherListItem.dtTxt
            val temp = weatherListItem.main.temp
            val weatherEntity = WeatherEntity(cityId = cityId, description = description, dateTxt = dateTxt, temp = temp, id = UUID.randomUUID().toString())
            weatherRepository.save(weatherEntity)
            Log.d("CityChooseFragment", "saveWeatherToDb: $weatherEntity")
        }
    }
}
