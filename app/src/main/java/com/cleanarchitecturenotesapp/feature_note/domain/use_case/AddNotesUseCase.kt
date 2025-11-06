package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.exceptions.InvalidNoteException
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

/**
 * Use case for adding or updating a note.
 * Validates note data before insertion.
 *
 * @param repository The note repository for data access
 */
class AddNotesUseCase(
    private val repository: NoteRepository
) {

    /**
     * Validates and inserts/updates a note.
     * Throws InvalidNoteException if validation fails.
     *
     * @param note The note to add or update
     * @throws InvalidNoteException If title or content is blank
     */
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