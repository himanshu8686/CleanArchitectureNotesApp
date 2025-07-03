package com.cleanarchitecturenotesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cleanarchitecturenotesapp.feature_note.domain.model.Note
import com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.cleanarchitecturenotesapp.feature_note.presentation.notes.NotesScreen
import com.cleanarchitecturenotesapp.feature_note.presentation.util.ScreenRoutes
import com.cleanarchitecturenotesapp.feature_work_manager.presentation.image_color_filter.ImageColorFilterScreen
import com.cleanarchitecturenotesapp.ui.theme.CleanArchitectureNotesAppTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitectureNotesAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoutes.ImageColorFilterScreenRoute
                    ) {
                        composable<ScreenRoutes.ImageColorFilterScreenRoute> {
                            ImageColorFilterScreen()
                        }

                        composable<ScreenRoutes.NotesScreenRoute> {
                            NotesScreen(
                                navController = navController
                            )
                        }

                        composable<ScreenRoutes.AddEditNoteScreenRoute> {

                            val args = it.toRoute<ScreenRoutes.AddEditNoteScreenRoute>()
                            val note =
                                args.note?.let { Gson().fromJson(args.note, Note::class.java) }

                            AddEditNoteScreen(
                                navController = navController,
                                note = note
                            )
                        }
                    }
                }
            }
        }
    }
}