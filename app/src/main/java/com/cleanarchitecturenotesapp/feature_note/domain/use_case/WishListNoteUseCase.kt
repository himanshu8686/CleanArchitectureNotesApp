package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository

class WishListNoteUseCase(
    private val repository: NoteRepository
) {
    /*suspend operator fun invoke(id: Int, isWishListed: Boolean) {
        repository.wishListNote(id = id, isWishListed = isWishListed )
    }*/
    /**
     * Updates the wishlist status of a note.
     *
     * @param note The note with updated wishlist status
     */
    suspend operator fun invoke(note: Note) {
        repository.wishListNote(note)
    }
}