package zelimkhan.magomadov.notes.data.note.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val text: String = "",
    val type: NoteType = NoteType.NOTE
)