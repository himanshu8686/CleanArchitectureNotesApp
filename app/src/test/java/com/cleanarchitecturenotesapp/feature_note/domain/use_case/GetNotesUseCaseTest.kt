package com.cleanarchitecturenotesapp.feature_note.domain.use_case

import com.cleanarchitecturenotesapp.feature_note.data.repositoryImpl.FakeNoteRepositoryImpl
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.domain.util.NoteOrder
import com.cleanarchitecturenotesapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {
    private lateinit var getNotes: GetNotesUseCase
    private lateinit var fakeNoteRepositoryImpl: FakeNoteRepositoryImpl

    @Before
    fun setUp() {
        fakeNoteRepositoryImpl = FakeNoteRepositoryImpl()
        getNotes = GetNotesUseCase(repository = fakeNoteRepositoryImpl)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }

        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach {fakeNoteRepositoryImpl.insertNote(it)}
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val noteOrder = NoteOrder.Title(orderType = OrderType.Ascending)
        val notes = getNotes(noteOrder = noteOrder).first()
        for (i in 0..notes.size-2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val noteOrder = NoteOrder.Title(orderType = OrderType.Descending)
        val notes = getNotes(noteOrder = noteOrder).first()
        for (i in 0..notes.size-2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

}