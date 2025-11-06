package com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecturenotesapp.feature_note.domain.exceptions.InvalidNoteException
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.use_case.NoteUseCases
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Add/Edit Note screen.
 * Manages note editing state and handles note save operations.
 *
 * @param noteUseCases Container for all note-related use cases
 * @param savedStateHandle Saved state handle for retrieving navigation arguments
 */
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var currentNoteId:Int ?= null

    init {
        val noteJson = savedStateHandle.get<String>("note")
        val note = Gson().fromJson(noteJson, Note::class.java)

        if (note != null) {
            if (note.id != null && note.id != -1){
                viewModelScope.launch {
                    noteUseCases.getSingleNoteUseCase(id= note.id)?.also { note ->
                        currentNoteId = note.id

                        // setting title
                        onEvent(AddEditNotesEvent.OnNoteTitleChanged(
                            title = note.title
                        ))

                        // setting content
                        onEvent(AddEditNotesEvent.OnNoteContentChanged(
                            content = note.content
                        ))

                        // Setting note color
                        onEvent(AddEditNotesEvent.ChangeColor(
                            color = note.color
                        ))
                    }
                }
            }
        }
    }
    private val _state = mutableStateOf(AddEditNoteState())
    /**
     * Current state of the add/edit note screen.
     */
    val state: State<AddEditNoteState> = _state

    /**
     * Shared flow for one-time UI events (e.g., navigation, snackbar).
     */
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * Handles user events from the UI.
     *
     * @param event The event to handle
     */
    fun onEvent(event: AddEditNotesEvent) {
        when(event) {
            is AddEditNotesEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNotesUseCase(
                            Note(
                                id = currentNoteId,
                                title = state.value.noteTitle,
                                content = state.value.noteContent,
                                timestamp = System.currentTimeMillis(),
                                color = state.value.noteColor
                            )
                        )
                        _eventFlow.emit(UiEvent.OnSaveNoteSuccess)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = e.message ?: "Couldn't save note")
                        )
                    }
                }
            }

            is AddEditNotesEvent.ChangeColor -> {
                _state.value = state.value.copy(
                    noteColor = event.color
                )
            }

            is AddEditNotesEvent.OnNoteContentChanged -> {
                _state.value = state.value.copy(
                    noteContent = event.content
                )
            }

            is AddEditNotesEvent.OnNoteTitleChanged -> {
                _state.value = state.value.copy(
                    noteTitle = event.title
                )
            }
        }
    }
}