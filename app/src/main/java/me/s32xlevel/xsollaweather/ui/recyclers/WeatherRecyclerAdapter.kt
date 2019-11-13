package me.s32xlevel.xsollaweather.ui.recyclers

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_item.view.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.model.WeatherEntity
import me.s32xlevel.xsollaweather.util.WeatherUtil

class WeatherRecyclerAdapter(private val weathers: List<WeatherEntity>) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.weather_item,
                parent,
                false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        with(holder.itemView) {
            weather_time.text = weathers[position].dateTxt.split(" ")[1]
            weather_image.setImageBitmap(BitmapFactory.decodeResource(resources,
                WeatherUtil.getWeatherImageResourceFromDescription(weathers[position].description)))
            weather_temp.text = "${(weathers[position].temp - 273).toInt()}°"
        }
    }

    override fun getItemCount() = weathers.size

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view)
}