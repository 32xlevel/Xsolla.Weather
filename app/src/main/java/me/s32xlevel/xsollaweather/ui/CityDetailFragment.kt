package me.s32xlevel.xsollaweather.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_city_detail.*
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.ui.recyclers.DatesRecyclerAdapter
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment

class CityDetailFragment : Fragment(R.layout.fragment_city_detail) {

    companion object {
        // Префы + сравнение времени посл. обновления и текущего
        fun newInstance(): Fragment {
            return CityDetailFragment()
        }
    }

    private val cityRepository by lazy { App.getInstance().getDatabase().cityRepository() }

    private val weatherRepository by lazy { App.getInstance().getDatabase().weatherRepository() }

    private val lastUpdate: Long by lazy { requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        .getLong("LastNetworkConnect", -1) }

    private val currentCityId: Int by lazy { requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        .getInt("SavedCity", -1) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
        configureRecyclerForDates()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.changeFragment(CityChooseFragment.newInstance(), cleanStack = true)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        val city = cityRepository.findById(currentCityId)
        with((activity as AppCompatActivity).supportActionBar!!) {
            title = city.name
            setDisplayHomeAsUpEnabled(true) // TODO В зависимости от того был ли при первом заходе выбран
        }
        setHasOptionsMenu(true)
    }

    private fun configureRecyclerForDates() {
        with(dates_rv) {
            dates_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            dates_rv.adapter = DatesRecyclerAdapter(linkedMapOf(11 to "Пн", 12 to "Вт", 13 to "Ср", 14 to "Чт")).apply {
                setOnDateClickListener { (dayNumber, dayName) -> }
            }
        }
    }
}
