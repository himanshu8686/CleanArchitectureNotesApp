package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

/**
 * Use case for retrieving a single note by ID.
 *
 * @param repository The note repository for data access
 */
class GetSingleNoteUseCase(
    private val repository: NoteRepository
) {
    /**
     * Retrieves a single note by its ID.
     *
     * @param id The ID of the note to retrieve
     * @return The note if found, null otherwise
     */
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}