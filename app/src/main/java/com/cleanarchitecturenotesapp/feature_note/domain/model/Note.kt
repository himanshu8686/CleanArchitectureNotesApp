package com.cleanarchitecturenotesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cleanarchitecturenotesapp.ui.theme.LightGreen
import com.cleanarchitecturenotesapp.ui.theme.OceanBlue
import com.cleanarchitecturenotesapp.ui.theme.RedOrange
import com.cleanarchitecturenotesapp.ui.theme.RedPink
import com.cleanarchitecturenotesapp.ui.theme.Violet
import kotlinx.serialization.Serializable

/**
 * Data class representing a Note entity.
 * Used for Room database and serialization.
 *
 * @property id Unique identifier for the note (auto-generated)
 * @property title Title of the note
 * @property content Content/body of the note
 * @property timestamp Timestamp when the note was created/modified
 * @property color Color value for the note background
 * @property isWishListed Whether the note is marked as favorite/wishlisted
 */
@Entity
@Serializable
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val isWishListed: Boolean = false
) {
    companion object {
        /**
         * List of available colors for notes.
         */
        val noteColors = listOf(RedPink, OceanBlue, RedOrange, LightGreen, Violet)
    }
}
