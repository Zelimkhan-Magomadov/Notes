package zelimkhan.magomadov.notes.ui.core.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class SelectableAdapter<T, VH : SelectableViewHolder<T>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {

    abstract fun onEnableSelectionMode()

    abstract fun onDisableSelectionMode()

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (isSelected(position)) {
            holder.select()
        } else {
            holder.deselect()
        }
    }

    private val positionOfSelectedItems = HashSet<Int>()

    private fun selectionModeEnabled(): Boolean {
        return positionOfSelectedItems.isNotEmpty()
    }

    private fun isSelected(position: Int): Boolean {
        return positionOfSelectedItems.contains(position)
    }

    private fun addToSelected(position: Int) {
        positionOfSelectedItems.add(position)
    }

    private fun removeFromSelection(position: Int) {
        positionOfSelectedItems.remove(position)
    }

    private fun toggleSelection(holder: SelectableViewHolder<T>, position: Int) {
        if (isSelected(position)) {
            removeFromSelection(position)
            holder.deselect()
            if (!selectionModeEnabled()) {
                onDisableSelectionMode()
            }
        } else {
            if (!selectionModeEnabled()) {
                onEnableSelectionMode()
            }
            addToSelected(position)
            holder.select()
        }
    }

    interface ItemSelectListener {

        fun onToggleSelection()

        fun onClick()
    }

    protected fun setSelectable(
        holder: SelectableViewHolder<T>,
        view: View,
        listener: ItemSelectListener
    ) {

        view.setOnClickListener {
            if (selectionModeEnabled()) {
                toggleSelection(holder, holder.adapterPosition)
                listener.onToggleSelection()
            } else {
                listener.onClick()
            }
        }

        view.setOnLongClickListener {
            toggleSelection(holder, holder.adapterPosition)
            listener.onToggleSelection()
            return@setOnLongClickListener true
        }
    }

    fun getSelectedItems(): List<T> {
        return positionOfSelectedItems.map { getItem(it) }
    }

    public override fun getItem(position: Int): T {
        return super.getItem(position)
    }

    fun getSelectedItemsCount(): Int {
        return positionOfSelectedItems.size
    }

    fun disableSelectionMode() {
        positionOfSelectedItems.clear()
        notifyItemRangeChanged(0, itemCount)
    }
}