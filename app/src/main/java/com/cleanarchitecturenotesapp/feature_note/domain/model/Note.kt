package com.cleanarchitecturenotesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cleanarchitecturenotesapp.ui.theme.LightGreen
import com.cleanarchitecturenotesapp.ui.theme.OceanBlue
import com.cleanarchitecturenotesapp.ui.theme.RedOrange
import com.cleanarchitecturenotesapp.ui.theme.RedPink
import com.cleanarchitecturenotesapp.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(RedPink, OceanBlue, RedOrange, LightGreen, Violet)
    }
}
