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
    @Upsert(Note::class)
    suspend fun insertOrUpdateNotes(vararg notes: Note)

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id= :id")
    suspend fun getNoteById(id:Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    //@Query("UPDATE note SET isWishListed = :isWishListed WHERE id =:id")
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun wishListNote(note: Note)
}