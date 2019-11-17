package me.s32xlevel.xsollaweather.presentation.citydetail

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.s32xlevel.xsollaweather.presentation.util.CustomLinearDividerItemDecoration

class EqualSpacingDates(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount

        val pxSpacing = CustomLinearDividerItemDecoration.dpToPx(spacing)
        with(outRect) {
            left = pxSpacing
            right = if (position == itemCount - 1) pxSpacing else 0
            top = pxSpacing
            bottom = pxSpacing
        }
    }


}