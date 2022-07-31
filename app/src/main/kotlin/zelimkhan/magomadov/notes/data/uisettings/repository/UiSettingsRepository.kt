package zelimkhan.magomadov.notes.data.uisettings.repository

import androidx.lifecycle.LiveData
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.UiSettingsDao
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.model.UiSettings

class UiSettingsRepository(
    private val localDataSource: UiSettingsDao
) {
    suspend fun save(settings: UiSettings) {
        localDataSource.save(settings)
    }

    fun get(): LiveData<UiSettings> {
        return localDataSource.get()
    }
}