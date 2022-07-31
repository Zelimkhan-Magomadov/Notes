package zelimkhan.magomadov.notes.data.note.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.data.note.datasource.local.model.NoteType

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(note: Note): Long

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun get(id: Long): Note

    @Query("SELECT * FROM notes WHERE type = :type ORDER BY id DESC")
    fun getList(type: NoteType): LiveData<List<Note>>

    @Delete
    suspend fun delete(notes: List<Note>)
}