package zelimkhan.magomadov.notes.data.uisettings.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.model.UiSettings

@Dao
interface UiSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(settings: UiSettings)

    @Query("SELECT * FROM ui_settings")
    fun get(): LiveData<UiSettings>
}