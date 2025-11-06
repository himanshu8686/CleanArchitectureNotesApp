package com.cleanarchitecturenotesapp.feature_note.domain.use_case

/**
 * Container class for all note-related use cases.
 * Provides a single point of access to all use cases.
 *
 * @property getNotesUseCase Use case for retrieving notes
 * @property deleteNoteUseCase Use case for deleting notes
 * @property addNotesUseCase Use case for adding/updating notes
 * @property getSingleNoteUseCase Use case for retrieving a single note
 * @property wishListNoteUseCase Use case for updating wishlist status
 */
data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNotesUseCase: AddNotesUseCase,
    val getSingleNoteUseCase: GetSingleNoteUseCase,
    val wishListNoteUseCase: WishListNoteUseCase
)