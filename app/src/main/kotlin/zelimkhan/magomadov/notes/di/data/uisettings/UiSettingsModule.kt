package zelimkhan.magomadov.notes.di.data.uisettings

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.notes.data.core.LocalDatabase
import zelimkhan.magomadov.notes.data.uisettings.datasource.local.UiSettingsDao
import zelimkhan.magomadov.notes.data.uisettings.repository.UiSettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UiSettingsModule {
    @Provides
    @Singleton
    fun provideUiSettingsRepository(localDataSource: UiSettingsDao): UiSettingsRepository {
        return UiSettingsRepository(localDataSource = localDataSource)
    }

    @Provides
    fun provideLocalDataSource(database: LocalDatabase): UiSettingsDao {
        return database.uiSettingsDao
    }
}