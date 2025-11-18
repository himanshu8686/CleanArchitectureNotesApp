package com.cleanarchitecturenotesapp.feature_note.data.repositoryImpl

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepositoryImpl: NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow {
            emit(notes)
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun wishListNote(note: Note) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            val existingNote = notes[index]
            notes[index] = existingNote.copy(isWishListed = !existingNote.isWishListed)
        }
    }
}