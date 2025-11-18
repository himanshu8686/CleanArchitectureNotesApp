package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    /**
     * Deletes a note from the repository.
     *
     * @param note The note to delete
     */
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}