package me.s32xlevel.xsollaweather.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.finded_city_item.view.*
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.R

class AddCityRecyclerAdapter : RecyclerView.Adapter<AddCityRecyclerAdapter.ViewHolder>() {
    private val cityRepository = App.getInstance().getDatabase().cityRepository()
    private val data = cityRepository.getAllNotSaved()

    private lateinit var onClickListener: (cityId: Int) -> Unit

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
        holder.itemView.setOnClickListener { onClickListener.invoke(data[position].id) }
    }

    override fun getItemCount() = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setOnClickListener(onClickListener: (cityId: Int) -> Unit) {
        this.onClickListener = onClickListener
    }
}