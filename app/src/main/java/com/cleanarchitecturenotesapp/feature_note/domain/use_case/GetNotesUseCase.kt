package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.cleanarchitecturenotesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Descending)
    ): Flow<List<Note>>{
        return repository.getNotes().map { note ->

            when (noteOrder.orderType) {
                is OrderType.Ascending -> {

                    when(noteOrder) {
                        is NoteOrder.Title -> {
                            note.sortedBy { it.title.lowercase(Locale.getDefault()) }
                        }
                        is NoteOrder.Date -> {
                            note.sortedBy { it.timestamp }
                        }
                        is NoteOrder.Color -> {
                            note.sortedBy { it.color }
                        }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> {
                            note.sortedByDescending { it.title.lowercase(Locale.getDefault()) }
                        }
                        is NoteOrder.Date -> {
                            note.sortedByDescending { it.timestamp }
                        }
                        is NoteOrder.Color -> {
                            note.sortedByDescending { it.color }
                        }
                    }
                }
            }
        }
    }
}