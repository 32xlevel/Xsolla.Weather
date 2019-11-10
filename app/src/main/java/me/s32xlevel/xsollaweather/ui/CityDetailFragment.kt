package me.s32xlevel.xsollaweather.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import me.s32xlevel.xsollaweather.R

class CityDetailFragment : Fragment(R.layout.fragment_city_detail) {

    companion object {
        private const val CITY_ARGUMENT = "CityArgument"

        fun newInstance(cityName: String): Fragment {
            return CityDetailFragment().apply { arguments = bundleOf(CITY_ARGUMENT to cityName) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureToolbar()
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
}
