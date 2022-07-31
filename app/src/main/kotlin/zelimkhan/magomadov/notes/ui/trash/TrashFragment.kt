package zelimkhan.magomadov.notes.ui.trash

import android.os.Bundle
import android.view.View
import zelimkhan.magomadov.notes.R
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType
import zelimkhan.magomadov.notes.ui.core.swipe.SwipeDecorator
import zelimkhan.magomadov.notes.ui.list.NoteActionType
import zelimkhan.magomadov.notes.ui.list.NotesListFragment

class TrashFragment : NotesListFragment() {
    override val noteType = NoteType.DELETED
    override val onEmptyListImageRes = R.drawable.empty_trash
    override val onEmptyListStringRes = R.string.empty_notes_list_trash
    override val swipeRightAction = NoteActionType.DELETE
    override val swipedLeftAction = NoteActionType.RESTORE

    override fun customizeSwipeDecorator(decorator: SwipeDecorator) = decorator.apply {
        swipeRightText = getString(R.string.delete)
        swipeLeftText = getString(R.string.restore)
        swipeRightIcon = resources.getDrawable(R.drawable.ic_delete_filled, null)
        swipeLeftIcon = resources.getDrawable(R.drawable.ic_restore_from_trash_filled, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppbarMenu()
    }

    private fun setupAppbarMenu() {
        binding.toolbar.menu.findItem(R.id.menu_empty_trash).isVisible = true
    }
}