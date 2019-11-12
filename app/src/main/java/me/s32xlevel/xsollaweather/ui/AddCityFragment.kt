package me.s32xlevel.xsollaweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.finded_city_item.view.*
import kotlinx.android.synthetic.main.fragment_add_city.*
import me.s32xlevel.xsollaweather.App

import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.repository.CityRepository
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment

class AddCityFragment : Fragment(R.layout.fragment_add_city) {

    companion object {
        fun newInstance() = AddCityFragment()
    }

    private val cityRepository by lazy { App.getInstance().getDatabase().cityRepository() }

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
            title = "Добавить город"
            setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    private fun configureRecycler() {
        with(finded_city_rv) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = AddCityRecyclerAdapter().apply {
                setOnClickListener { cityId, cityName ->
                    activity?.changeFragment(CityDetailFragment.newInstance()) // TODO: Префы.
                    cityRepository.save(cityId)
                }
            }
        }
    }
}

class AddCityRecyclerAdapter : RecyclerView.Adapter<AddCityRecyclerAdapter.ViewHolder>() {
    private val cityRepository = App.getInstance().getDatabase().cityRepository()
    private val data = cityRepository.getAllNotSaved()

    private lateinit var onClickListener: (cityId: Int, cityName: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.finded_city_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.finded_city_tv.text = data[position].name
        holder.itemView.setOnClickListener { onClickListener.invoke(data[position].id, data[position].name) }
    }

    override fun getItemCount() = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setOnClickListener(onClickListener: (cityId: Int, cityName: String) -> Unit) {
        this.onClickListener = onClickListener
    }
}


