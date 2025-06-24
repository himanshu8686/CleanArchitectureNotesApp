package com.cleanarchitecturenotesapp.di

import android.app.Application
import androidx.room.Room
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDao(db: NoteDatabase): NoteDao = db.noteDao

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application, notesProvider: Provider<NoteDao>): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME)
            .addCallback(
                RoomDBInitializer(notesProvider = notesProvider)
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = noteDatabase.noteDao)
    }

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

}