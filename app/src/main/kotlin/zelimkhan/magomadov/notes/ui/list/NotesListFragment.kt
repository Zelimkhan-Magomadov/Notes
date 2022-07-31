package zelimkhan.magomadov.notes.ui.list

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import zelimkhan.magomadov.notes.NavigationGraphDirections
import zelimkhan.magomadov.notes.R
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.model.ViewType
import zelimkhan.magomadov.notes.databinding.FragmentNotesListBinding
import zelimkhan.magomadov.notes.ui.core.extension.connectToolbarToNavigationController
import zelimkhan.magomadov.notes.ui.core.swipe.SwipeDecorator
import zelimkhan.magomadov.notes.ui.list.adapter.NotesAdapter
import zelimkhan.magomadov.notes.ui.list.helper.NoteSwipeDecorator
import zelimkhan.magomadov.notes.ui.list.helper.NoteTouchHelperCallback

@AndroidEntryPoint
abstract class NotesListFragment : Fragment(R.layout.fragment_notes_list) {
    protected lateinit var binding: FragmentNotesListBinding
    private val viewModel: NotesListViewModel by viewModels()
    private val notesAdapter = NotesAdapter()
    private lateinit var touchHelper: ItemTouchHelper

    protected abstract val noteType: NoteType
    protected abstract val onEmptyListImageRes: Int
    protected abstract val onEmptyListStringRes: Int

    protected abstract val swipeRightAction: NoteActionType
    protected abstract val swipedLeftAction: NoteActionType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNoteType(noteType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesListBinding.bind(view)
        connectToolbarToNavigationController(binding.toolbar)
        setupAppbar()
        setupSwipe()
        setupAdapter()
        setupRecyclerView()
        setupUiStateObserver()
        setupUiSettingsObserver()
    }

    private fun setupAppbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_switch_to_list_view -> viewModel.switchView(ViewType.LIST)
                R.id.menu_switch_to_grid_view -> viewModel.switchView(ViewType.GRID)
                R.id.menu_empty_trash -> viewModel.deleteNotes(notesAdapter.currentList)
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setupSwipe() {
        val swipeDecorator: SwipeDecorator = NoteSwipeDecorator(
            iconMargin = resources.getDimensionPixelOffset(R.dimen.swipe_icon_margin),
            swipeTextMargin = resources.getDimensionPixelOffset(R.dimen.swipe_text_margin),
            swipeTextSize = resources.getDimensionPixelOffset(R.dimen.swipe_text_size),
            backgroundCornerRadius = resources.getDimensionPixelOffset(R.dimen.swipe_background_corner_radius),
        ).apply {
            swipeThresholdColor = Color.RED
        }
        val callback = NoteTouchHelperCallback(
            onSwipeRight = {
                viewModel.actionOnTheNote(notesAdapter.getItem(it), swipeRightAction)
            },
            onSwipeLeft = {
                viewModel.actionOnTheNote(notesAdapter.getItem(it), swipedLeftAction)
            },
            swipeDecorator = customizeSwipeDecorator(swipeDecorator)
        )
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.notesList)
    }

    protected abstract fun customizeSwipeDecorator(decorator: SwipeDecorator): SwipeDecorator

    private fun setupAdapter() {
        notesAdapter.actionListener = object : NotesAdapter.ActionListener {
            override fun onNoteClick(id: Long) = openNote(id)
        }
    }

    protected fun openNote(id: Long) {
        val direction = NavigationGraphDirections.actionToNoteFragment()
        direction.noteId = id
        findNavController().navigate(direction)
    }

    protected fun createNote() {
        openNote(0)
    }

    private fun setupRecyclerView() {
        binding.notesList.adapter = notesAdapter
        setViewType(ViewType.GRID)
        setupOnEmptyListBackground()
    }

    private fun setupOnEmptyListBackground() {
        binding.emptyListImage.setImageResource(onEmptyListImageRes)
        binding.emptyListLabel.setText(onEmptyListStringRes)
    }

    private fun setupUiStateObserver() {
        viewModel.notesUiState.observe(viewLifecycleOwner) { notesList ->
            notesAdapter.submitList(notesList)
            binding.emptyListBackground.isVisible = notesList.isEmpty()
            binding.toolbar.menu.findItem(R.id.menu_empty_trash).isEnabled = notesList.isNotEmpty()
        }
    }

    private fun setupUiSettingsObserver() {
        viewModel.uiSettings.observe(viewLifecycleOwner) { settings ->
            settings?.let {
                setViewType(settings.viewType)
            }
        }
    }

    private fun setViewType(type: ViewType) {
        binding.notesList.layoutManager = when (type) {
            ViewType.GRID -> switchToGridView()
            ViewType.LIST -> switchToListView()
        }
    }

    private fun switchToGridView(): RecyclerView.LayoutManager {
        binding.toolbar.menu.findItem(R.id.menu_switch_to_grid_view).isVisible = false
        binding.toolbar.menu.findItem(R.id.menu_switch_to_list_view).isVisible = true
        return StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }

    private fun switchToListView(): RecyclerView.LayoutManager {
        binding.toolbar.menu.findItem(R.id.menu_switch_to_list_view).isVisible = false
        binding.toolbar.menu.findItem(R.id.menu_switch_to_grid_view).isVisible = true
        return LinearLayoutManager(requireContext())
    }
}