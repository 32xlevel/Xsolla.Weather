package me.s32xlevel.xsollaweather.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_city.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.ui.presenter.AddCityPresenter
import me.s32xlevel.xsollaweather.ui.presenter.AddCityView
import me.s32xlevel.xsollaweather.ui.recyclers.AddCityAdapter
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.setToPreferences

class AddCityFragment : BaseFragment(R.layout.fragment_add_city), AddCityView {

    companion object {
        fun newInstance() = AddCityFragment()
    }

    private lateinit var presenter: AddCityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = AddCityPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        configureRecycler()
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
        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun configureRecycler() {
        with(finded_city_rv) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = AddCityAdapter().apply { setOnClickListener { presenter.onCityClickListener(it) } }
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


