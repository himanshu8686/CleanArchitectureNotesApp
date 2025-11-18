package com.cleanarchitecturenotesapp.feature_note.data.data_source

import androidx.compose.ui.graphics.toArgb
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Provider

class RoomDBInitializer(
    private val notesProvider: Provider<NoteDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    /**
     * Called when the database is created for the first time.
     * Populates the database with initial sample notes.
     *
     * @param db The database instance
     */
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateNotes()
        }
    }

    /**
     * Populates the database with initial sample notes.
     */
    private suspend fun populateNotes() {
        notesProvider.get().insertOrUpdateNotes(*notesGenerator.take(10).toList().toTypedArray())
    }
}

/**
 * This is a [Sequence] generator to generate random notes.
 */
val notesGenerator = generateSequence {

    Note(
        title = "title_${UUID.randomUUID().toString().replace("-", "").substring(0, 6)}",
        content = "content_${UUID.randomUUID().toString().replace("-", "").substring(0, 6)}",
        timestamp = System.currentTimeMillis(),
        color = Note.noteColors.random().toArgb(),
        isWishListed = false
    )
}