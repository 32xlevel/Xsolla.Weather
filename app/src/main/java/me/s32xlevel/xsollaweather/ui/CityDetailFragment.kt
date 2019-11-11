package me.s32xlevel.xsollaweather.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_city_detail.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.ui.recyclers.DatesRecyclerAdapter

class CityDetailFragment : Fragment(R.layout.fragment_city_detail) {

    companion object {
        private const val CITY_ARGUMENT = "CityArgument"

        fun newInstance(cityName: String): Fragment {
            return CityDetailFragment().apply { arguments = bundleOf(CITY_ARGUMENT to cityName) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
        configureRecyclerForDates()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.supportFragmentManager?.popBackStack()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = arguments?.getString(CITY_ARGUMENT)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun configureRecyclerForDates() {
        val adapter = DatesRecyclerAdapter(linkedMapOf(11 to "Пн", 12 to "Вт", 13 to "Ср", 14 to "Чт"))
        adapter.setOnDateClickListener { (dayNumber, dayName) -> }
        dates_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dates_rv.adapter = adapter
    }
}
