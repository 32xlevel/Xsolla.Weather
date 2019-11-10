package me.s32xlevel.xsollaweather.ui.recyclers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_card_item.view.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.model.CityChoose

class CityChooseRecyclerAdapter(private val data: List<CityChoose>) :
    RecyclerView.Adapter<CityChooseRecyclerAdapter.CityMainViewHolder>() {

    private var onClickListener: (cityName: String) -> Unit = {}
    private var onLongClickListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityMainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_card_item,
                parent,
                false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CityMainViewHolder, position: Int) {
        val cityChoose = data[position]
        holder.itemView.city_name_tv.text = cityChoose.name
        holder.itemView.weather_iv.setImageBitmap(cityChoose.weatherImage)
        holder.itemView.weather_range_tv.text = "${cityChoose.tempMin}° .. ${cityChoose.tempMax}°"

        holder.itemView.setOnClickListener { onClickListener.invoke(it.city_name_tv.text.toString()) }
        holder.itemView.setOnLongClickListener { onLongClickListener.invoke(); true }
    }

    override fun getItemCount() = data.size

    class CityMainViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setOnCityClickListener(onClickListener: (cityName: String) -> Unit) {
        this.onClickListener = onClickListener
    }

    fun setOnCityLongClickListener(onLongClickListener: () -> Unit) {
        this.onLongClickListener = onLongClickListener
    }
}