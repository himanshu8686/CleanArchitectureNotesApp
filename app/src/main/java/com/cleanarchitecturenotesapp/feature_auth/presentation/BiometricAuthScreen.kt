package com.cleanarchitecturenotesapp.feature_auth.presentation

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cleanarchitecturenotesapp.feature_note.presentation.util.ScreenRoutes
import kotlinx.coroutines.launch

/**
 * Composable function that displays the biometric authentication screen.
 *
 * This screen provides:
 * - A beautiful, modern UI for biometric authentication
 * - Automatic biometric prompt on screen appearance
 * - Skip authentication option
 * - Error handling and user feedback
 * - Navigation to NotesScreen on successful authentication
 *
 * @param navController Navigation controller for navigating to NotesScreen
 * @param viewModel ViewModel managing the authentication state
 */
@Composable
fun BiometricAuthScreen(
    navController: NavController,
    viewModel: BiometricAuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onEvent(BiometricAuthEvent.CheckAvailability)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Navigate to NotesScreen on successful authentication
    LaunchedEffect(state.authenticationSuccess) {
        if (state.authenticationSuccess) {
            navController.navigate(ScreenRoutes.NotesScreenRoute) {
                // Clear the back stack so user can't go back to auth screen
                popUpTo(ScreenRoutes.BiometricAuthScreenRoute) {
                    inclusive = true
                }
            }
        }
    }

    // Show error messages in snackbar
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(error)
                viewModel.clearError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Animated fingerprint icon
                BiometricIcon(
                    modifier = Modifier.size(120.dp),
                    isLoading = state.isLoading
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Title
                Text(
                    text = "Secure Access",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subtitle
                Text(
                    text = if (state.isBiometricAvailable) {
                        "Use your fingerprint or face to unlock"
                    } else {
                        "Biometric authentication is not available"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                if (state.needsEnrollment) {
                    OutlinedButton(
                        onClick = { launchBiometricEnrollment(context) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(
                            text = "Set up biometrics",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Authenticate button
                if (state.isBiometricAvailable) {
                    Button(
                        onClick = {
                            val activity = context as? FragmentActivity
                            activity?.let {
                                viewModel.authenticate(it)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Authenticate",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Skip button
                OutlinedButton(
                    onClick = {
                        viewModel.onEvent(BiometricAuthEvent.SkipAuth)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !state.isLoading
                ) {
                    Text(
                        text = "Skip for now",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }

    // Auto-trigger authentication when screen appears and biometric is available
    LaunchedEffect(state.isBiometricAvailable, state.authenticationSuccess) {
        if (state.isBiometricAvailable && !state.isLoading && !state.authenticationSuccess) {
            val activity = context as? FragmentActivity
            activity?.let {
                viewModel.authenticate(it)
            }
        }
    }
}

/**
 * Composable function that displays an animated biometric icon.
 *
 * @param modifier Modifier to be applied to the icon
 * @param isLoading Whether the icon should show loading animation
 */
@Composable
private fun BiometricIcon(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    val scale by animateFloatAsState(
        targetValue = if (isLoading) 1.1f else 1f,
        animationSpec = tween(durationMillis = 1000),
        label = "icon_scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isLoading) 0.7f else 1f,
        animationSpec = tween(durationMillis = 1000),
        label = "icon_alpha"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = CircleShape
                )
        )
        Icon(
            imageVector = Icons.Default.Fingerprint,
            contentDescription = "Biometric Authentication",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Launches the system biometric enrollment settings so the user can register fingerprints or face data.
 *
 * @param context Current context used to start the settings activity
 */
private fun launchBiometricEnrollment(context: android.content.Context) {
    val activity = context as? Activity ?: return
    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
        putExtra(
            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )
    }
    activity.startActivity(enrollIntent)
}


