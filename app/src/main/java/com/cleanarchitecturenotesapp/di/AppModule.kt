package com.cleanarchitecturenotesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.cleanarchitecturenotesapp.feature_note.data.data_source.NoteDao
import com.cleanarchitecturenotesapp.feature_note.data.data_source.NoteDatabase
import com.cleanarchitecturenotesapp.feature_note.data.data_source.RoomDBInitializer
import com.cleanarchitecturenotesapp.feature_note.data.respositoryImpl.NoteRepositoryImpl
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.AddNotesUseCase
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.DeleteNoteUseCase
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.GetNotesUseCase
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.GetSingleNoteUseCase
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.NoteUseCases
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.WishListNoteUseCase
import com.cleanarchitecturenotesapp.feature_work_manager.data.remote.FileApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Dagger Hilt module providing dependency injection for the application.
 * All dependencies are provided as singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides NoteDao instance from the database.
     *
     * @param db The NoteDatabase instance
     * @return NoteDao instance for database operations
     */
    @Provides
    @Singleton
    fun provideUserDao(db: NoteDatabase): NoteDao = db.noteDao

    /**
     * Provides NoteDatabase instance with Room database builder.
     * Includes database initialization callback for populating initial data.
     *
     * @param app Application instance
     * @param notesProvider Provider for NoteDao to initialize database
     * @return Configured NoteDatabase instance
     */
    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application, notesProvider: Provider<NoteDao>): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .addCallback(
                RoomDBInitializer(notesProvider = notesProvider)
            )
            .build()
    }

    /**
     * Provides NoteRepository implementation.
     *
     * @param noteDatabase The NoteDatabase instance
     * @return NoteRepositoryImpl instance
     */
    @Provides
    @Singleton
    fun providesNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = noteDatabase.noteDao)
    }

    /**
     * Provides NoteUseCases container with all use case instances.
     *
     * @param repository The NoteRepository instance
     * @return NoteUseCases containing all note-related use cases
     */
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository = repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository = repository),
            addNotesUseCase = AddNotesUseCase(repository = repository),
            getSingleNoteUseCase = GetSingleNoteUseCase(repository = repository),
            wishListNoteUseCase = WishListNoteUseCase(repository = repository)
        )
    }

    /**
     * Provides FileApiService for downloading images.
     *
     * @return Configured FileApiService instance
     */
    @Provides
    @Singleton
    fun provideFileApiService(): FileApiService {
        return Retrofit.Builder()
            .baseUrl("https://images.unsplash.com")
            .build()
            .create(FileApiService::class.java)
    }

    /**
     * Provides WorkManager instance for background work.
     *
     * @param context Application context
     * @return WorkManager instance
     */
    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}