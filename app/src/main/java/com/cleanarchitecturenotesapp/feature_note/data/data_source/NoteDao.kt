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

@Dao
interface NoteDao {
    /**
     * Inserts or updates multiple notes in the database.
     *
     * @param notes Variable number of notes to insert or update
     */
    @Upsert(Note::class)
    suspend fun insertOrUpdateNotes(vararg notes: Note)

    /**
     * Retrieves all notes from the database as a Flow.
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
     * Inserts a note into the database, replacing if it already exists.
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
     * Updates the wishlist status of a note.
     *
     * @param note The note with updated wishlist status
     */
    //@Query("UPDATE note SET isWishListed = :isWishListed WHERE id =:id")
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun wishListNote(note: Note)
}