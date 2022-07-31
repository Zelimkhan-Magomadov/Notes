package zelimkhan.magomadov.notes.di.data.note

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zelimkhan.magomadov.notes.data.core.LocalDatabase
import zelimkhan.magomadov.notes.data.note.datasource.local.NoteDao
import zelimkhan.magomadov.notes.data.note.repository.NoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {
    @Provides
    @Singleton
    fun provideRepository(localDataSource: NoteDao): NoteRepository {
        return NoteRepository(localDataSource = localDataSource)
    }

    @Provides
    fun provideLocalDataSource(database: LocalDatabase): NoteDao {
        return database.noteDao
    }
}