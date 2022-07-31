package zelimkhan.magomadov.notes.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType
import zelimkhan.magomadov.notes.data.note.repository.NoteRepository
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.model.UiSettings
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.model.ViewType
import zelimkhan.magomadov.notes.data.uisettings.repository.UiSettingsRepository
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val uiSettingsRepository: UiSettingsRepository
) : ViewModel() {
    val uiSettings = uiSettingsRepository.get()

    private val notesType = MutableLiveData<NoteType>()
    val notesUiState = Transformations.switchMap(notesType) {
        noteRepository.getList(it)
    }

    fun setNoteType(type: NoteType) {
        notesType.value = type
    }

    fun switchView(type: ViewType) {
        viewModelScope.launch {
            uiSettingsRepository.save(UiSettings(viewType = type))
        }
    }

    fun deleteNotes(notes: List<Note>) {
        viewModelScope.launch {
            noteRepository.delete(notes)
        }
    }

    fun actionOnTheNote(note: Note, actionType: NoteActionType) {
        viewModelScope.launch {
            when (actionType) {
                NoteActionType.MOVE_TO_TRASH -> {
                    noteRepository.save(note.copy(type = NoteType.DELETED))
                }
                NoteActionType.RESTORE,
                NoteActionType.UNARCHIVE -> {
                    noteRepository.save(note.copy(type = NoteType.NOTE))
                }
                NoteActionType.ARCHIVE -> {
                    noteRepository.save(note.copy(type = NoteType.ARCHIVED))
                }
                NoteActionType.DELETE -> {
                    noteRepository.delete(listOf(note))
                }
            }
        }
    }
}