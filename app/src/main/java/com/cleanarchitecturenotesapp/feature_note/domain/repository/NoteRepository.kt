package com.cleanarchitecturenotesapp.feature_note.domain.repository

import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for note data operations.
 * Defines the contract for note data access.
 */
interface NoteRepository {

    /**
     * Retrieves all notes as a Flow.
     *
     * @return Flow emitting a list of all notes
     */
    fun getNotes(): Flow<List<Note>>

    /**
     * Retrieves a single note by its ID.
     *
     * @param id The ID of the note to retrieve
     * @return The note if found, null otherwise
     */
    suspend fun getNoteById(id: Int): Note?

    /**
     * Inserts a new note into the database.
     *
     * @param note The note to insert
     */
    suspend fun insertNote(note: Note)

    /**
     * Deletes a note from the database.
     *
     * @param note The note to delete
     */
    suspend fun deleteNote(note: Note)

    /**
     * Updates the wishlist status of a note.
     *
     * @param note The note with updated wishlist status
     */
    suspend fun wishListNote(note: Note)
}