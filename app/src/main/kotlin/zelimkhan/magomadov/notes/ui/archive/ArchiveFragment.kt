package zelimkhan.magomadov.notes.ui.archive

import zelimkhan.magomadov.notes.R
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType
import zelimkhan.magomadov.notes.ui.core.swipe.SwipeDecorator
import zelimkhan.magomadov.notes.ui.list.NoteActionType
import zelimkhan.magomadov.notes.ui.list.NotesListFragment

class ArchiveFragment : NotesListFragment() {
    override val noteType = NoteType.ARCHIVED
    override val onEmptyListImageRes = R.drawable.empty_archive
    override val onEmptyListStringRes = R.string.empty_notes_list_archive
    override val swipeRightAction = NoteActionType.MOVE_TO_TRASH
    override val swipedLeftAction = NoteActionType.UNARCHIVE

    override fun customizeSwipeDecorator(decorator: SwipeDecorator) = decorator.apply {
        swipeRightText = getString(R.string.delete)
        swipeLeftText = getString(R.string.unarchive)
        swipeRightIcon = resources.getDrawable(R.drawable.ic_delete_filled, null)
        swipeLeftIcon = resources.getDrawable(R.drawable.ic_unarchive_filled, null)
    }
}