package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

class GetSingleNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}