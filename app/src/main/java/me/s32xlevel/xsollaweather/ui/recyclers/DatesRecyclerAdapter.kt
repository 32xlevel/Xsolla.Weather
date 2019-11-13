package me.s32xlevel.xsollaweather.ui.recyclers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.date_item.view.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.util.WeatherUtil

class DatesRecyclerAdapter(private val dates: Set<String>) :
    RecyclerView.Adapter<DatesRecyclerAdapter.DatesViewHolder>() {

    private var selectedDay = dates.elementAt(0)

    private lateinit var onClickListener: (date: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DatesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.date_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        val fullDate = dates.elementAt(position)
        val numOfMonth = fullDate.split("-")[2]
        val dayOfWeek = WeatherUtil.getDayOfWeek(fullDate)

        with(holder.itemView) {
            day_number_tv.text = numOfMonth
            day_name_tv.text = dayOfWeek

            if (fullDate == selectedDay) {
                day_number_tv.setBackgroundResource(R.drawable.date_oval_background)
                day_number_tv.setTextColor(Color.WHITE)
            } else {
                day_number_tv.setBackgroundResource(R.drawable.empty_background)
                day_number_tv.setTextColor(Color.BLACK)
            }

            setOnClickListener {
                selectedDay = fullDate
                onClickListener.invoke(fullDate)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = dates.size

    class DatesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setOnClickListener(onClickListener: (date: String) -> Unit) {
        this.onClickListener = onClickListener
    }
}