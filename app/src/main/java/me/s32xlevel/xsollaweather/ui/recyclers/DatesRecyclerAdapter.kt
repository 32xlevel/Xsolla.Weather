package me.s32xlevel.xsollaweather.ui.recyclers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.date_item.view.*
import me.s32xlevel.xsollaweather.R

// 10: Вс, 11: Пн, 12: Вт
// LinkedHashMap, т.к. очень важен порядок следования элементов
class DatesRecyclerAdapter(private val daysOfWeekAndName: LinkedHashMap<Int, String>) :
    RecyclerView.Adapter<DatesRecyclerAdapter.DatesViewHolder>() {

    private var selectedDay
            = (daysOfWeekAndName.keys.toIntArray()[0] to daysOfWeekAndName.values.toTypedArray()[0])

    private var onClickListener: (Pair<Int, String>) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DatesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.date_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        val day =
            (daysOfWeekAndName.keys.toIntArray()[position] to daysOfWeekAndName.values.toTypedArray()[position])
        with(holder.itemView) {
            day_number_tv.text = day.first.toString()
            day_name_tv.text = day.second

            if (day.first == selectedDay.first && day.second == selectedDay.second) {
                day_number_tv.setBackgroundResource(R.drawable.date_oval_background)
                day_number_tv.setTextColor(Color.WHITE)
            } else {
                day_number_tv.setBackgroundResource(R.drawable.empty_background)
                day_number_tv.setTextColor(Color.BLACK)
            }

            setOnClickListener {
                selectedDay = day
                onClickListener.invoke(day)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = daysOfWeekAndName.size

    class DatesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setOnDateClickListener(onClickListener: (Pair<Int, String>) -> Unit) {
        this.onClickListener = onClickListener
    }
}