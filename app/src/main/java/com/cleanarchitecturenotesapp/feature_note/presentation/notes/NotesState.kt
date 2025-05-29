package com.cleanarchitecturenotesapp.feature_note.presentation.notes

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.cleanarchitecturenotesapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
