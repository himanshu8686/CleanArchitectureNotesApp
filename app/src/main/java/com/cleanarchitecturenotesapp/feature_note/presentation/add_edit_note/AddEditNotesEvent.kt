package com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

sealed class AddEditNotesEvent{
    data class OnNoteTitleChanged(val title: String): AddEditNotesEvent()
    data class OnNoteContentChanged(val content: String): AddEditNotesEvent()
    data object SaveNote: AddEditNotesEvent()
    data class ChangeColor(val color:Int): AddEditNotesEvent()
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    data object OnSaveNoteSuccess: UiEvent()
}