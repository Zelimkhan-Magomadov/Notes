package zelimkhan.magomadov.notes.ui.list.helper

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import zelimkhan.magomadov.notes.ui.core.swipe.SwipeDecorator

class NoteTouchHelperCallback(
    private val onSwipeRight: (position: Int) -> Unit,
    private val onSwipeLeft: (position: Int) -> Unit,
    private val swipeDecorator: SwipeDecorator
) : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.RIGHT) onSwipeRight.invoke(viewHolder.adapterPosition)
        else onSwipeLeft.invoke(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
            swipeDecorator.decorate(c, recyclerView, dX, viewHolder)
    }
}