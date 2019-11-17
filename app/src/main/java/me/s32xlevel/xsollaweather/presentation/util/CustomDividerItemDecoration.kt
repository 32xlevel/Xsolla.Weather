package me.s32xlevel.xsollaweather.presentation.util

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class CustomLinearDividerItemDecoration(
    @ColorInt
    dividerColor: Int = Color.LTGRAY,
    private val dividerLineWidthPx: Int = dpToPx(
        0.7
    ),
    private val linePaddingLeftPx: Int = 0,
    private val linePaddingRightPx: Int = 0,
    val drawBeforeFirst: Boolean = false,
    val drawAfterLast: Boolean = false
) : RecyclerView.ItemDecoration() {

    companion object {
        fun dpToPx(dpValue: Number): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue.toFloat(),
                Resources.getSystem().displayMetrics
            ).toInt()
        }
    }

    private val paint = Paint().apply {
        color = dividerColor
        strokeWidth = dividerLineWidthPx.toFloat()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.adapterItemCount == 0) return
        val itemPos = parent.getChildAdapterPosition(view)
        if (itemPos == 0 && drawBeforeFirst) {
            outRect.top = dividerLineWidthPx
        }
        val totalCnt = parent.adapter?.itemCount ?: return
        if (itemPos < totalCnt - 1 || itemPos == totalCnt - 1 && drawAfterLast) {
            outRect.bottom = dividerLineWidthPx
        }
    }

    private val RecyclerView.adapterItemCount : Int
        get() {
            return adapter?.let { it.itemCount } ?: 0
        }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.adapterItemCount == 0) return

        val left = parent.paddingLeft.toFloat() + linePaddingLeftPx
        val right = (parent.width - parent.paddingRight - linePaddingRightPx).toFloat()
        lateinit var view: View

        val lastIndex = if (drawAfterLast) parent.childCount else (parent.childCount - 1)

        c.save()

        if (drawBeforeFirst) {
            view = parent.getChildAt(0) ?: return
            c.drawLine(
                left,
                view.top.toFloat(),
                right,
                view.top.toFloat(), paint
            )
        }

        for (itemIndex in 0 until lastIndex) {
            view = parent.getChildAt(itemIndex) ?: return
            c.drawLine(
                left,
                view.bottom.toFloat(),
                right,
                view.bottom.toFloat(), paint
            )
        }
        c.restore()
    }
}