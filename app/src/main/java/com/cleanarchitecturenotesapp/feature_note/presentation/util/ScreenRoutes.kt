package com.cleanarchitecturenotesapp.feature_note.presentation.util
import kotlinx.serialization.Serializable

/**
 * Sealed class representing all navigation routes in the application.
 */
sealed class ScreenRoutes{

    /**
     * Route to the main concepts screen.
     */
    @Serializable
    data object MainConceptScreenRoute: ScreenRoutes()

    /**
     * Route to the image color filter screen.
     */
    @Serializable
    data object ImageColorFilterScreenRoute: ScreenRoutes()

    /**
     * Route to the notes list screen.
     */
    @Serializable
    data object NotesScreenRoute: ScreenRoutes()

    /**
     * Route to the add/edit note screen.
     *
     * @property note Optional JSON string representation of the note to edit
     */
    @Serializable
    data class AddEditNoteScreenRoute(
        val note: String ?= null
    ): ScreenRoutes()
}