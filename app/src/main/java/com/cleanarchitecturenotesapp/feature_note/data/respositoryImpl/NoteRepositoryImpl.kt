package com.cleanarchitecturenotesapp.feature_note.data.respositoryImpl

import com.cleanarchitecturenotesapp.feature_note.data.data_source.NoteDao
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    /**
     * Retrieves all notes from the database as a Flow.
     *
     * @return Flow emitting a list of all notes
     */
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    /**
     * Retrieves a single note by its ID.
     *
     * @param id The ID of the note to retrieve
     * @return The note if found, null otherwise
     */
    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    /**
     * Inserts or updates a note in the database.
     *
     * @param note The note to insert or update
     */
    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    /**
     * Deletes a note from the database.
     *
     * @param note The note to delete
     */
    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    /**
     * Updates the wishlist status of a note.
     *
     * @param note The note with updated wishlist status
     */
    override suspend fun wishListNote(note: Note/*id: Int, isWishListed: Boolean*/) {
        //return noteDao.wishListNote(id = id, isWishListed = isWishListed)
        return noteDao.wishListNote(note)
    }
}