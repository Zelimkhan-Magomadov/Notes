package zelimkhan.magomadov.notes.ui.notes

import android.os.Bundle
import android.view.View
import zelimkhan.magomadov.notes.R
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType
import zelimkhan.magomadov.notes.ui.core.swipe.SwipeDecorator
import zelimkhan.magomadov.notes.ui.list.NoteActionType
import zelimkhan.magomadov.notes.ui.list.NotesListFragment

class NotesFragment : NotesListFragment() {
    override val noteType = NoteType.NOTE
    override val onEmptyListImageRes = R.drawable.empty_notes_list
    override val onEmptyListStringRes = R.string.empty_notes_list
    override val swipeRightAction = NoteActionType.MOVE_TO_TRASH
    override val swipedLeftAction = NoteActionType.ARCHIVE

    override fun customizeSwipeDecorator(decorator: SwipeDecorator) = decorator.apply {
        swipeRightText = getString(R.string.delete)
        swipeLeftText = getString(R.string.archive)
        swipeRightIcon = resources.getDrawable(R.drawable.ic_delete_filled, null)
        swipeLeftIcon = resources.getDrawable(R.drawable.ic_archive_filled, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab()
    }

    private fun setupFab() {
        binding.newNote.setOnClickListener { createNote() }
        binding.newNote.show()
    }
}