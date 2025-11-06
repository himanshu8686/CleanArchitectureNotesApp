package com.cleanarchitecturenotesapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNotesUseCase: AddNotesUseCase,
    val getSingleNoteUseCase: GetSingleNoteUseCase,
    val wishListNoteUseCase: WishListNoteUseCase
)