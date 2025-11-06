package com.cleanarchitecturenotesapp.feature_note.presentation.notes

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    //data class WishListNote(val id: Int, val isWishListed: Boolean): NotesEvent()
    data class WishListNote(val note: Note): NotesEvent()
    data object RestoreNote: NotesEvent()
    data object ToggleOrderSection: NotesEvent()
}