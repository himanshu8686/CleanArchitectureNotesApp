package com.cleanarchitecturenotesapp.feature_note.presentation.notes

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.cleanarchitecturenotesapp.feature_note.domain.util.OrderType

/**
 * State class representing the current state of the Notes screen.
 *
 * @property notes List of notes to display
 * @property noteOrder Current ordering/sorting configuration
 * @property isOrderSectionVisible Whether the order section is visible
 */
data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
