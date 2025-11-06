package com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note

data class AddEditNoteState(
    val noteColor: Int = Note.noteColors.random().toArgb(),
    val noteTitle: String = "",
    val noteContent: String = ""
)