package com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

/**
 * Sealed class representing all possible events in the Add/Edit Note screen.
 */
sealed class AddEditNotesEvent{
    /**
     * Event when the note title changes.
     */
    data class OnNoteTitleChanged(val title: String): AddEditNotesEvent()
    
    /**
     * Event when the note content changes.
     */
    data class OnNoteContentChanged(val content: String): AddEditNotesEvent()
    
    /**
     * Event to save the note.
     */
    data object SaveNote: AddEditNotesEvent()
    
    /**
     * Event to change the note color.
     */
    data class ChangeColor(val color:Int): AddEditNotesEvent()
}

/**
 * Sealed class representing UI events for one-time actions (navigation, snackbar).
 */
sealed class UiEvent {
    /**
     * Event to show a snackbar with a message.
     */
    data class ShowSnackBar(val message: String): UiEvent()
    
    /**
     * Event indicating successful note save.
     */
    data object OnSaveNoteSuccess: UiEvent()
}