package com.cleanarchitecturenotesapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note


/**
 * Room database for storing notes.
 *
 * @property noteDao Data Access Object for note operations
 */
@Database(
    entities = [Note::class],
    exportSchema = true,
    version = 2
)
abstract class NoteDatabase: RoomDatabase() {
    /**
     * Provides access to note database operations.
     */
    abstract val noteDao: NoteDao

    companion object {
        /**
         * Database name constant.
         */
        const val DATABASE_NAME = "notes_db"
    }
}