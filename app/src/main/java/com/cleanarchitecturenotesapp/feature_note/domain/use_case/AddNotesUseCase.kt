package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.exceptions.InvalidNoteException
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

class AddNotesUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {

        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty")
        }

        repository.insertNote(note = note)
    }
}