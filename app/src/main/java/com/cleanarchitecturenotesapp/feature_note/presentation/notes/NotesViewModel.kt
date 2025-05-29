package com.cleanarchitecturenotesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.NoteUseCases
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.cleanarchitecturenotesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var getNotesJob: Job ?= null
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(
            noteOrder = NoteOrder.Date(orderType = OrderType.Descending)
        )
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.Order -> {
                if (state.value.noteOrder == event.noteOrder && state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }

                getNotes(event.noteOrder)

            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNotesUseCase(note = recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder = noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}