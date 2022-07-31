package zelimkhan.magomadov.notes.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note

class NoteDiffUtilCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
}