package com.cleanarchitecturenotesapp.feature_note.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cleanarchitecturenotesapp.feature_note.presentation.util.ScreenRoutes

@Composable
fun MainConceptsScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(route = ScreenRoutes.ImageColorFilterScreenRoute)
            },
            content = {
                Text("work manager concept")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(route = ScreenRoutes.NotesScreenRoute)
            },
            content = {
                Text("Clean architecture notes")
            }
        )
    }
}