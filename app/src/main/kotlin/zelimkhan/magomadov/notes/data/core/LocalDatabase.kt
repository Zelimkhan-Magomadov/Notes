package zelimkhan.magomadov.notes.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import zelimkhan.magomadov.notes.data.note.datasource.local.NoteDao
import zelimkhan.magomadov.notes.data.note.datasource.local.model.Note
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.UiSettingsDao
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.model.UiSettings

@Database(entities = [Note::class, UiSettings::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val uiSettingsDao: UiSettingsDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}