package zelimkhan.magomadov.notes.ui.core.swipe

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

interface SwipeDecorator {
    val iconMargin: Int
    val swipeTextMargin: Int
    val swipeTextSize: Int
    val backgroundCornerRadius: Int
    val cornerSmoothing: Int
    var swipeBackgroundColor: Int
    var swipeRightBackgroundColor: Int
    var swipeLeftBackgroundColor: Int
    var swipeThresholdColor: Int
    var swipeRightThresholdColor: Int
    var swipeLeftThresholdColor: Int
    var swipeIcon: Drawable?
    var swipeRightIcon: Drawable?
    var swipeLeftIcon: Drawable?
    var swipeText: String
    var swipeRightText: String
    var swipeLeftText: String
    var swipeTextColor: Int

    fun decorate(
        canvas: Canvas,
        recyclerView: RecyclerView,
        dX: Float,
        viewHolder: RecyclerView.ViewHolder
    )
}