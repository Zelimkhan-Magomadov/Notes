package zelimkhan.magomadov.notes.data.note.repository

import androidx.lifecycle.LiveData
import zelimkhan.magomadov.notes.data.note.datasource.local.NoteDao
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType

class NoteRepository(
    private val localDataSource: NoteDao
) {
    suspend fun save(note: Note): Long {
        return localDataSource.save(note)
    }

    suspend fun get(id: Long): Note {
        return localDataSource.get(id)
    }

    fun getList(type: NoteType): LiveData<List<Note>> {
        return localDataSource.getList(type)
    }

    suspend fun delete(notes: List<Note>) {
        localDataSource.delete(notes)
    }
}