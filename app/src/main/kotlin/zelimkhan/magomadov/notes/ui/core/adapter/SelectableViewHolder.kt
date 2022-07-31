package zelimkhan.magomadov.notes.ui.core.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SelectableViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun select()

    abstract fun deselect()

    abstract fun bind(data: T)
}
