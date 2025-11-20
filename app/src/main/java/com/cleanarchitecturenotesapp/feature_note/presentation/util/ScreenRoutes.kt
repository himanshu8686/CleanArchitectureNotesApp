package com.cleanarchitecturenotesapp.feature_note.presentation.util
import kotlinx.serialization.Serializable

sealed class ScreenRoutes{

    @Serializable
    data object BiometricAuthScreenRoute: ScreenRoutes()

    @Serializable
    data object MainConceptScreenRoute: ScreenRoutes()

    @Serializable
    data object ImageColorFilterScreenRoute: ScreenRoutes()

    @Serializable
    data object NotesScreenRoute: ScreenRoutes()

    @Serializable
    data class AddEditNoteScreenRoute(
        val note: String ?= null
    ): ScreenRoutes()
}