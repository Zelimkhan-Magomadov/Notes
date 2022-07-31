package zelimkhan.magomadov.notes.ui.list.adapter

import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.databinding.NoteItemBinding
import zelimkhan.magomadov.notes.ui.core.adapter.SelectableViewHolder

class NotesViewHolder(
    private val binding: NoteItemBinding
) : SelectableViewHolder<Note>(binding.root) {
    override fun select() {
        binding.root.isChecked = true
    }

    override fun deselect() {
        binding.root.isChecked = false
    }

    override fun bind(data: Note) {
        binding.noteTitle.text = data.title
        binding.noteText.text = data.text
    }
}