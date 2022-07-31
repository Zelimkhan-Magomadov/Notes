package zelimkhan.magomadov.notes.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.databinding.NoteItemBinding
import zelimkhan.magomadov.notes.ui.core.adapter.SelectableAdapter

class NotesAdapter : SelectableAdapter<Note, NotesViewHolder>(NoteDiffUtilCallback()) {
    interface ActionListener {
        fun onNoteClick(id: Long)
    }

    var actionListener: ActionListener? = null

    override fun onEnableSelectionMode() {
        // todo
    }

    override fun onDisableSelectionMode() {
        // todo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NotesViewHolder(binding).apply {
            setSelectable(this, itemView, object : ItemSelectListener {
                override fun onToggleSelection() {
                    // todo
                }

                override fun onClick() {
                    actionListener?.onNoteClick(getItem(adapterPosition).id)
                }
            })
        }
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(getItem(position))
    }
}