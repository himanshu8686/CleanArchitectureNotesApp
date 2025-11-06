package com.cleanarchitecturenotesapp.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for Note entity.
 * Provides database operations for notes.
 */
@Dao
interface NoteDao {
    /**
     * Inserts or updates multiple notes.
     *
     * @param notes Variable number of notes to insert or update
     */
    @Upsert(Note::class)
    suspend fun insertOrUpdateNotes(vararg notes: Note)

    /**
     * Retrieves all notes as a Flow.
     *
     * @return Flow emitting a list of all notes
     */
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    /**
     * Retrieves a single note by its ID.
     *
     * @param id The ID of the note to retrieve
     * @return The note if found, null otherwise
     */
    @Query("SELECT * FROM note WHERE id= :id")
    suspend fun getNoteById(id:Int): Note?

    /**
     * Inserts a new note or replaces if it already exists.
     *
     * @param note The note to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    /**
     * Deletes a note from the database.
     *
     * @param note The note to delete
     */
    @Delete
    suspend fun deleteNote(note: Note)

    /**
     * Updates a note (used for wishlist status updates).
     *
     * @param note The note with updated information
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun wishListNote(note: Note)
}