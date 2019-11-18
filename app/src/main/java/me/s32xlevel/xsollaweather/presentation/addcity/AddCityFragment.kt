package me.s32xlevel.xsollaweather.presentation.addcity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_city.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.presentation.BaseFragment
import me.s32xlevel.xsollaweather.presentation.citydetail.CityDetailFragment
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.setToPreferences

class AddCityFragment : BaseFragment(R.layout.fragment_add_city), AddCityView {

    companion object {
        fun newInstance() = AddCityFragment()
    }

    private lateinit var presenter: AddCityPresenter

    private lateinit var cityAdapter: AddCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = AddCityPresenter(this)
        cityAdapter = AddCityAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        configureRecycler()

        search_et.addTextChangedListener(onTextChanged = { text, start, count, after ->
            if (text == null || text.isBlank()) {
                cityAdapter.data = cityRepository.getAllNotSaved()
            } else {
                val newList = cityAdapter.data.filter { it.name.contains(text, ignoreCase = true) }
                cityAdapter.data = newList
            }
        })
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

    private fun configureToolbar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_add_title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun configureRecycler() {
        with(finded_city_rv) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = cityAdapter.apply { setOnClickListener { presenter.onCityClickListener(it) } }
        }
    }

    override fun saveCityIdToPrefs(id: Int) {
        context?.setToPreferences(PreferencesManager.SAVED_CITY, id)
    }

    override fun saveCityIdToDb(id: Int) {
        cityRepository.save(id)
    }

    override fun goToNextFragment() {
        changeFragment(CityDetailFragment.newInstance(), cleanStack = true)
    }

    override fun goToBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onDetach() {
        presenter.onDetachView()
        super.onDetach()
    }
}


