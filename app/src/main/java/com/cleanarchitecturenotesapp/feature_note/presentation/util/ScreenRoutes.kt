package com.cleanarchitecturenotesapp.feature_note.presentation.util

sealed class ScreenRoutes(val route: String){
    data object NotesScreen: ScreenRoutes("notes_screen")
    data object AddEditNoteScreen: ScreenRoutes("add_edit_notes_screen")
}