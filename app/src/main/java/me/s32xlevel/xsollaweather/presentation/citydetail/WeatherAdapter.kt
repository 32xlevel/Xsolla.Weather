package me.s32xlevel.xsollaweather.presentation.citydetail

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_item.view.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.business.model.WeatherEntity
import me.s32xlevel.xsollaweather.util.WeatherUtil

class WeatherAdapter(private val weathers: List<WeatherEntity>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

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
            val time = weathers[position].dateTxt.split(" ")[1].split(":")
            weather_time.text = "${time[0]}:${time[1]}"
            weather_image.setImageBitmap(BitmapFactory.decodeResource(resources,
                WeatherUtil.getWeatherImageResourceFromDescription(weathers[position].description)))
            weather_temp.text = "${(weathers[position].temp - 273).toInt()}°"
        }
    }

    override fun getItemCount() = weathers.size

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view)
}