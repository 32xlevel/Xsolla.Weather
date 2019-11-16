package me.s32xlevel.xsollaweather.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_city.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.ui.recyclers.AddCityAdapter
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.setToPreferences

class AddCityFragment : BaseFragment(R.layout.fragment_add_city) {

    companion object {
        fun newInstance() = AddCityFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        configureRecycler()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.supportFragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        with((activity as AppCompatActivity).supportActionBar!!) {
            title = getString(R.string.fragment_add_title)
            setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    private fun configureRecycler() {
        with(finded_city_rv) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = AddCityAdapter().apply {
                setOnClickListener { cityId ->
                    context?.setToPreferences(PreferencesManager.SAVED_CITY, cityId)
                    changeFragment(CityDetailFragment.newInstance())
                    cityRepository.save(cityId)
                }
            }
        }
    }
}


