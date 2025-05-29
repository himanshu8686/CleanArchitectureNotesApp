package com.cleanarchitecturenotesapp.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color(note.color))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onDeleteClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete_Note",
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    }
}