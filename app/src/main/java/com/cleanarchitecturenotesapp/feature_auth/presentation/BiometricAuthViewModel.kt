package com.cleanarchitecturenotesapp.feature_auth.presentation

import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleanarchitecturenotesapp.feature_auth.domain.manager.BiometricAuthManager
import com.cleanarchitecturenotesapp.feature_auth.domain.manager.BiometricAuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed class representing UI events for biometric authentication.
 */
sealed class BiometricAuthEvent {
    object Authenticate : BiometricAuthEvent()
    object SkipAuth : BiometricAuthEvent()
    object CheckAvailability : BiometricAuthEvent()
}

/**
 * Data class representing the state of the biometric authentication screen.
 *
 * @property isLoading Indicates if authentication is in progress
 * @property isBiometricAvailable Indicates if biometric authentication is available
 * @property errorMessage Error message to display, if any
 * @property authenticationSuccess Indicates if authentication was successful
 */
data class BiometricAuthState(
    val isLoading: Boolean = false,
    val isBiometricAvailable: Boolean = false,
    val errorMessage: String? = null,
    val authenticationSuccess: Boolean = false,
    val needsEnrollment: Boolean = false
)

/**
 * ViewModel for managing biometric authentication state and business logic.
 *
 * This ViewModel handles:
 * - Checking biometric availability
 * - Triggering biometric authentication
 * - Managing authentication state
 * - Handling authentication results
 *
 * @param biometricAuthManager Manager for biometric authentication operations
 */
@HiltViewModel
class BiometricAuthViewModel @Inject constructor(
    private val biometricAuthManager: BiometricAuthManager
) : ViewModel() {

    private val _state = MutableStateFlow(BiometricAuthState())
    val state: StateFlow<BiometricAuthState> = _state.asStateFlow()

    init {
        checkBiometricAvailability()
    }

    /**
     * Handles UI events related to biometric authentication.
     *
     * @param event The event to handle
     */
    fun onEvent(event: BiometricAuthEvent) {
        when (event) {
            is BiometricAuthEvent.Authenticate -> {
                // Authentication is triggered from the screen with activity context
            }
            is BiometricAuthEvent.SkipAuth -> {
                _state.value = _state.value.copy(authenticationSuccess = true)
            }
            is BiometricAuthEvent.CheckAvailability -> {
                checkBiometricAvailability()
            }
        }
    }

    /**
     * Triggers biometric authentication.
     *
     * @param activity The FragmentActivity hosting the biometric prompt
     */
    fun authenticate(activity: FragmentActivity) {
        if (!state.value.isBiometricAvailable) {
            _state.value = _state.value.copy(
                errorMessage = "Biometric authentication is not available"
            )
            return
        }

        _state.value = _state.value.copy(isLoading = true)

        biometricAuthManager.authenticate(
            activity = activity,
            title = "Biometric Authentication",
            subtitle = "Authenticate to access your notes",
            description = "Use your fingerprint or face to securely access the app",
            onResult = { result ->
                updateAuthResult(result)
            }
        )
    }

    /**
     * Updates the authentication result state.
     *
     * @param result The result of the biometric authentication
     */
    fun updateAuthResult(result: BiometricAuthResult) {
        _state.value = when (result) {
            is BiometricAuthResult.Success -> {
                _state.value.copy(
                    isLoading = false,
                    authenticationSuccess = true,
                    errorMessage = null,
                    needsEnrollment = false
                )
            }
            is BiometricAuthResult.Error -> {
                _state.value.copy(
                    isLoading = false,
                    errorMessage = result.errorMessage,
                    isBiometricAvailable = false,
                    needsEnrollment = result.errorCode == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
                )
            }
            is BiometricAuthResult.Failed -> {
                _state.value.copy(
                    isLoading = false,
                    errorMessage = result.errorMessage
                )
            }
            is BiometricAuthResult.Cancelled -> {
                _state.value.copy(
                    isLoading = false,
                    errorMessage = null
                )
            }
        }
    }

    /**
     * Sets the loading state.
     *
     * @param isLoading Whether authentication is in progress
     */
    fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }

    /**
     * Clears the error message.
     */
    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    /**
     * Checks if biometric authentication is available on the device.
     */
    private fun checkBiometricAvailability() {
        viewModelScope.launch {
            val result = biometricAuthManager.isBiometricAvailable()
            _state.value = when (result) {
                is BiometricAuthResult.Success -> {
                    _state.value.copy(
                        isBiometricAvailable = true,
                    errorMessage = null,
                    needsEnrollment = false
                    )
                }
                is BiometricAuthResult.Error -> {
                    _state.value.copy(
                        isBiometricAvailable = false,
                    errorMessage = result.errorMessage,
                    needsEnrollment = result.errorCode == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
                    )
                }
                else -> _state.value
            }
        }
    }
}

