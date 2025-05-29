package com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.ui.theme.appComponents.appTextField.AppTextField
import com.cleanarchitecturenotesapp.ui.theme.appComponents.appTextField.rememberAppTextFieldParams
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val snackBarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            initialValue = Color(
                if (noteColor != -1) noteColor else state.noteColor
            )
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event: UiEvent ->
            when (event) {
                is UiEvent.OnSaveNoteSuccess -> {
                    navController.navigateUp()
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNotesEvent.SaveNote)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Done, contentDescription = "Save note")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (state.noteColor == colorInt) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }

                                viewModel.onEvent(AddEditNotesEvent.ChangeColor(color = colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                params = rememberAppTextFieldParams(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.noteTitle,
                    placeholder = {
                        Text(
                            "Enter title",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    isOutlined = true,
                    onValueChange = {
                        viewModel.onEvent(AddEditNotesEvent.OnNoteTitleChanged(title = it))
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                params = rememberAppTextFieldParams(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.noteContent,
                    placeholder = {
                        Text(
                            "Enter content",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    },
                    minLines = 10,
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    isOutlined = true,
                    onValueChange = {
                        viewModel.onEvent(AddEditNotesEvent.OnNoteContentChanged(content = it))
                    }
                )
            )

        }
    }
}