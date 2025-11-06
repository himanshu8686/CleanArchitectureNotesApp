package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.cleanarchitecturenotesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

/**
 * Use case for retrieving and sorting notes.
 *
 * @param repository The note repository for data access
 */
class GetNotesUseCase(
    private val repository: NoteRepository
) {
    /**
     * Retrieves notes from the repository and applies sorting based on the specified order.
     *
     * @param noteOrder The ordering criteria (default: Date descending)
     * @return Flow emitting a sorted list of notes
     */
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
                        is NoteOrder.Fav -> {
                            note.sortedBy { it.isWishListed }
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

                        is NoteOrder.Fav -> {
                            note.sortedByDescending { it.isWishListed }
                        }
                    }
                }
            }
        }
    }
}