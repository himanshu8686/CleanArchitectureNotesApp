package com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note

/**
 * State class representing the current state of the Add/Edit Note screen.
 *
 * @property noteColor Current color of the note
 * @property noteTitle Current title text of the note
 * @property noteContent Current content text of the note
 */
data class AddEditNoteState(
    val noteColor: Int = Note.noteColors.random().toArgb(),
    val noteTitle: String = "",
    val noteContent: String = ""
)