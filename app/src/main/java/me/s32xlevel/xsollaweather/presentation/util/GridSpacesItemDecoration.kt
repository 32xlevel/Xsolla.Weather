package me.s32xlevel.xsollaweather.presentation.util

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacesItemDecoration(private val spaceInDp: Int) : RecyclerView.ItemDecoration(   ) {

    companion object {
        fun dpToPx(dpValue: Number): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue.toFloat(),
                Resources.getSystem().displayMetrics
            ).toInt()
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left =
            dpToPx(
                spaceInDp
            )
        outRect.bottom =
            dpToPx(
                spaceInDp
            )
    }
}