package zelimkhan.magomadov.notes.ui.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.data.note.repository.NoteRepository
import zelimkhan.magomadov.notes.ui.core.extension.liveData
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableLiveData<Note>()
    val uiState = _uiState.liveData

    init {
        viewModelScope.launch {
            val noteId = savedStateHandle.get<Long>("noteId")!!
            if (noteId == 0L) createNote()
            else fetchNote(noteId)
        }
    }

    private suspend fun createNote() {
        val noteId = noteRepository.save(Note())
        fetchNote(noteId)
    }

    private suspend fun fetchNote(noteId: Long) {
        _uiState.value = noteRepository.get(noteId)
    }

    fun saveNote(title: String, text: String) {
        viewModelScope.launch {
            _uiState.value?.let { note ->
                noteRepository.save(note.copy(title = title, text = text))
            }
        }
    }
}