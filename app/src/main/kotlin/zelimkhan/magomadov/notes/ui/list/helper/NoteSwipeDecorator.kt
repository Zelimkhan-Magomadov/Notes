package zelimkhan.magomadov.notes.ui.list.helper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.TextPaint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import zelimkhan.magomadov.notes.ui.core.swipe.SwipeDecorator
import kotlin.math.abs

class NoteSwipeDecorator(
    override val swipeTextSize: Int,
    override val swipeTextMargin: Int,
    override val iconMargin: Int,
    override val backgroundCornerRadius: Int,
) : SwipeDecorator {
    override var cornerSmoothing = backgroundCornerRadius * 5
    override var swipeBackgroundColor = Color.GRAY
        set(value) {
            swipeRightBackgroundColor = value
            swipeLeftBackgroundColor = value
            field = value
        }
    override var swipeRightBackgroundColor = swipeBackgroundColor
    override var swipeLeftBackgroundColor = swipeBackgroundColor
    override var swipeThresholdColor = Color.GRAY
        set(value) {
            swipeRightThresholdColor = value
            swipeLeftThresholdColor = value
            field = value
        }
    override var swipeRightThresholdColor = swipeThresholdColor
    override var swipeLeftThresholdColor = swipeThresholdColor
    override var swipeIcon: Drawable? = null
        set(value) {
            swipeRightIcon = value
            swipeLeftIcon = value
            field = value
        }
    override var swipeRightIcon: Drawable? = swipeIcon
    override var swipeLeftIcon: Drawable? = swipeIcon
    override var swipeText = ""
        set(value) {
            swipeRightText = value
            swipeLeftText = value
            field = value
        }
    override var swipeRightText = swipeText
    override var swipeLeftText = swipeText
    override var swipeTextColor = Color.WHITE
    private val background = GradientDrawable()
    private val textPaint = TextPaint()

    override fun decorate(
        canvas: Canvas,
        recyclerView: RecyclerView,
        dX: Float,
        viewHolder: RecyclerView.ViewHolder
    ) {
        if (dX > 0) decorateSwipeRight(canvas, recyclerView, dX, viewHolder)
        else if (dX < 0) decorateSwipeLeft(canvas, recyclerView, dX, viewHolder)
    }

    private fun decorateSwipeRight(
        canvas: Canvas,
        recyclerView: RecyclerView,
        dX: Float,
        viewHolder: RecyclerView.ViewHolder
    ) {
        drawSwipeRightBackground(canvas, recyclerView, dX, viewHolder.itemView)
        drawSwipeRightIcon(canvas, viewHolder.itemView)
        drawSwipeRightText(canvas, viewHolder.itemView)
    }

    private fun drawSwipeRightBackground(
        canvas: Canvas,
        recyclerView: RecyclerView,
        dX: Float,
        itemView: View
    ) {
        setSwipeRightBackgroundColor(recyclerView, dX)
        val rightBound = getSwipeRightBackgroundRightBound(dX, itemView)
        showSwipeRightBackground(itemView, rightBound, canvas)
    }

    private fun setSwipeRightBackgroundColor(recyclerView: RecyclerView, dX: Float) {
        val isThreshold = checkSwipeThreshold(recyclerView, dX)
        if (isThreshold) background.setColor(swipeRightThresholdColor)
        else background.setColor(swipeRightBackgroundColor)
    }

    private fun checkSwipeThreshold(recyclerView: RecyclerView, dX: Float) =
        abs(dX) > recyclerView.width / 2

    private fun getSwipeRightBackgroundRightBound(dX: Float, itemView: View) = when {
        dX > itemView.width - cornerSmoothing -> itemView.left + itemView.width
        else -> itemView.left + dX.toInt() + cornerSmoothing
    }

    private fun showSwipeRightBackground(itemView: View, rightBound: Int, canvas: Canvas) {
        background.setBounds(itemView.left, itemView.top, rightBound, itemView.bottom)
        background.cornerRadius = backgroundCornerRadius.toFloat()
        background.draw(canvas)
    }

    private fun drawSwipeRightIcon(canvas: Canvas, itemView: View) {
        swipeRightIcon?.let { icon ->
            icon.bounds = getRightIconBounds(icon, itemView)
            icon.draw(canvas)
        }
    }

    private fun getRightIconBounds(icon: Drawable, itemView: View): Rect {
        val top = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val bottom = top + icon.intrinsicHeight
        val left = itemView.left + iconMargin
        val right = itemView.left + iconMargin + icon.intrinsicWidth
        return Rect(left, top, right, bottom)
    }

    private fun drawSwipeRightText(canvas: Canvas, itemView: View) {
        if (swipeRightText.isEmpty()) return
        val textBounds = getTextBounds(swipeRightText)
        val iconSize = getIconSize(swipeRightIcon)
        val textHeight = abs(textBounds.exactCenterY())
        showSwipeRightText(canvas, itemView, iconSize, textHeight)
    }

    private fun getTextBounds(text: String): Rect {
        val bounds = Rect()
        textPaint.isAntiAlias = true
        textPaint.textSize = swipeTextSize.toFloat()
        textPaint.color = swipeTextColor
        textPaint.getTextBounds(text, 0, text.length, bounds)
        return bounds
    }

    private fun getIconSize(icon: Drawable?) = icon?.intrinsicHeight?.plus(iconMargin) ?: 0

    private fun showSwipeRightText(
        canvas: Canvas,
        itemView: View,
        iconSize: Int,
        textHeight: Float
    ) = canvas.drawText(
        swipeRightText,
        (itemView.left + iconSize + swipeTextMargin).toFloat(),
        (itemView.bottom + itemView.top) / 2f + textHeight,
        textPaint
    )

    // ============================ LEFT =============================

    private fun decorateSwipeLeft(
        canvas: Canvas,
        recyclerView: RecyclerView,
        dX: Float,
        viewHolder: RecyclerView.ViewHolder
    ) {
        drawSwipeLeftBackground(canvas, recyclerView, dX, viewHolder.itemView)
        drawSwipeLeftIcon(canvas, viewHolder.itemView)
        drawSwipeLeftText(canvas, viewHolder.itemView)
    }

    private fun drawSwipeLeftBackground(
        canvas: Canvas,
        recyclerView: RecyclerView,
        dX: Float,
        itemView: View
    ) {
        setSwipeLeftBackgroundColor(recyclerView, dX)
        val leftBound = getSwipeLeftBackgroundLeftBound(dX, itemView)
        showSwipeLeftBackground(itemView, leftBound, canvas)
    }

    private fun setSwipeLeftBackgroundColor(recyclerView: RecyclerView, dX: Float) {
        val isThreshold = checkSwipeThreshold(recyclerView, dX)
        if (isThreshold) background.setColor(swipeLeftThresholdColor)
        else background.setColor(swipeLeftBackgroundColor)
    }

    private fun getSwipeLeftBackgroundLeftBound(dX: Float, itemView: View) = when {
        dX * -1 > itemView.width - cornerSmoothing -> itemView.right - itemView.width
        else -> itemView.right + dX.toInt() - cornerSmoothing
    }

    private fun showSwipeLeftBackground(itemView: View, leftBound: Int, canvas: Canvas) {
        background.setBounds(leftBound, itemView.top, itemView.right, itemView.bottom)
        background.cornerRadius = backgroundCornerRadius.toFloat()
        background.draw(canvas)
    }

    private fun drawSwipeLeftIcon(canvas: Canvas, itemView: View) {
        swipeLeftIcon?.let { icon ->
            icon.bounds = getLeftIconBounds(icon, itemView)
            icon.draw(canvas)
        }
    }

    private fun getLeftIconBounds(icon: Drawable, itemView: View): Rect {
        val top = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val bottom = top + icon.intrinsicHeight
        val left = itemView.right - iconMargin - icon.intrinsicWidth
        val right = itemView.right - iconMargin
        return Rect(left, top, right, bottom)
    }

    private fun drawSwipeLeftText(canvas: Canvas, itemView: View) {
        if (swipeLeftText.isEmpty()) return
        val textBounds = getTextBounds(swipeLeftText)
        val iconSize = getIconSize(swipeLeftIcon)
        val textHeight = abs(textBounds.exactCenterY())
        val textWidth = textBounds.width()
        showSwipeLeftText(canvas, itemView, iconSize, textHeight, textWidth)
    }

    private fun showSwipeLeftText(
        canvas: Canvas,
        itemView: View,
        iconSize: Int,
        textHeight: Float,
        textWidth: Int
    ) = canvas.drawText(
        swipeLeftText,
        (itemView.right - (iconSize + textWidth + swipeTextMargin)).toFloat(),
        (itemView.bottom + itemView.top) / 2f + textHeight,
        textPaint
    )
}
