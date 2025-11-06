package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

/**
 * Use case for updating the wishlist status of a note.
 *
 * @param repository The note repository for data access
 */
class WishListNoteUseCase(
    private val repository: NoteRepository
) {
    /**
     * Updates the wishlist status of a note.
     *
     * @param note The note with updated wishlist status
     */
    suspend operator fun invoke(note: Note) {
        repository.wishListNote(note)
    }
}