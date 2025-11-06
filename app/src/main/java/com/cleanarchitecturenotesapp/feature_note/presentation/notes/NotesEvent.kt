package com.cleanarchitecturenotesapp.feature_note.presentation.notes

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder

/**
 * Sealed class representing all possible events in the Notes screen.
 */
sealed class NotesEvent {
    /**
     * Event to change the note ordering/sorting.
     */
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    
    /**
     * Event to delete a note.
     */
    data class DeleteNote(val note: Note): NotesEvent()
    
    /**
     * Event to toggle the wishlist status of a note.
     */
    data class WishListNote(val note: Note): NotesEvent()
    
    /**
     * Event to restore the recently deleted note.
     */
    data object RestoreNote: NotesEvent()
    
    /**
     * Event to toggle the visibility of the order section.
     */
    data object ToggleOrderSection: NotesEvent()
}