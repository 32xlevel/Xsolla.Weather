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
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.model.CityChoose
import me.s32xlevel.xsollaweather.ui.recyclers.CityChooseRecyclerAdapter
import me.s32xlevel.xsollaweather.ui.recyclers.GridSpacesItemDecoration
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment

class CityChooseFragment : Fragment(R.layout.fragment_city_choose) {

    companion object {
        fun newInstance() = CityChooseFragment()
    }

    private val cities = arrayListOf<CityChoose>()

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
        cities.add(CityChoose("Москва", BitmapFactory.decodeResource(resources, R.drawable.rainly), 0.0, 5.0))
        cities.add(CityChoose("Пермь", BitmapFactory.decodeResource(resources, R.drawable.cloudly), -6.0, -4.0))
    }

    private fun configureRecycler() {
        val adapter = CityChooseRecyclerAdapter(cities)
        adapter.setOnCityClickListener { activity?.changeFragment(CityDetailFragment.newInstance(it)) }
        cities_rv.layoutManager = GridLayoutManager(context, 2)
        cities_rv.adapter = adapter
        cities_rv.addItemDecoration(GridSpacesItemDecoration(8))
    }
}
