package com.cleanarchitecturenotesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cleanarchitecturenotesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.cleanarchitecturenotesapp.feature_note.presentation.notes.NotesScreen
import com.cleanarchitecturenotesapp.feature_note.presentation.util.ScreenRoutes
import com.cleanarchitecturenotesapp.ui.theme.CleanArchitectureNotesAppTheme
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
                    val  navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoutes.NotesScreen.route
                    ) {
                        composable(
                            route = ScreenRoutes.NotesScreen.route
                        ){
                            NotesScreen(
                                navController = navController
                            )
                        }

                        composable(
                            route = ScreenRoutes.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}