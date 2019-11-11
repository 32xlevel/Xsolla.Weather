package me.s32xlevel.xsollaweather.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_item.view.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.model.Weather
import me.s32xlevel.xsollaweather.model.WeatherForDate

class WeatherRecyclerAdapter(private val unparsedWeather: Weather, private val dateTxt: String) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>() {

    private val weatherList = toTransferObject(unparsedWeather)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.weather_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        with(holder.itemView) {
            weather_time.text = weatherList[position].time
            weather_image.setImageBitmap(weatherList[position].weatherImage)
            weather_temp.text = weatherList[position].temp.toString()
        }
    }

    override fun getItemCount() = 0

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun toTransferObject(unparsedWeather: Weather): List<WeatherForDate> {
        return emptyList()
    }
}