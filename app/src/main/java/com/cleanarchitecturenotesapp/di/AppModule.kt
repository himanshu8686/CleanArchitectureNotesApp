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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the NoteDao instance from the database.
     *
     * @param db The NoteDatabase instance
     * @return The NoteDao instance
     */
    @Provides
    @Singleton
    fun provideUserDao(db: NoteDatabase): NoteDao = db.noteDao

    /**
     * Provides the NoteDatabase instance with Room database builder.
     *
     * @param app The Application instance
     * @param notesProvider Provider for NoteDao to initialize the database
     * @return The NoteDatabase instance
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
     * Provides the NoteRepository implementation.
     *
     * @param noteDatabase The NoteDatabase instance
     * @return The NoteRepository implementation
     */
    @Provides
    @Singleton
    fun providesNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = noteDatabase.noteDao)
    }

    /**
     * Provides all note use cases as a single NoteUseCases instance.
     *
     * @param repository The NoteRepository instance
     * @return The NoteUseCases instance containing all use cases
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
     * Provides the FileApiService instance using Retrofit.
     *
     * @return The FileApiService instance
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
     * Provides the WorkManager instance for the application.
     *
     * @param context The application context
     * @return The WorkManager instance
     */
    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}