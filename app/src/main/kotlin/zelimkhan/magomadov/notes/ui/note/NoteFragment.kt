package zelimkhan.magomadov.notes.ui.note

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import zelimkhan.magomadov.notes.R
import zelimkhan.magomadov.notes.databinding.FragmentNoteBinding
import zelimkhan.magomadov.notes.ui.core.extension.connectToolbarToNavigationController

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note) {
    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)
        connectToolbarToNavigationController(binding.toolbar)
        setupNoteTitle()
        setupNoteText()
        setupUiStateObserver()
    }

    private fun setupNoteTitle() {
        binding.noteTitle.apply {
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setRawInputType(InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES))
        }
    }

    private fun setupNoteText() {
        binding.content.setOnClickListener {
            binding.noteText.requestFocus()
            binding.noteText.setSelection(binding.noteText.length())
        }
    }

    private fun setupUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { note ->
            binding.noteTitle.setText(note.title)
            binding.noteText.setText(note.text)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveNote(
            title = binding.noteTitle.text.toString(),
            text = binding.noteText.text.toString()
        )
    }
}
