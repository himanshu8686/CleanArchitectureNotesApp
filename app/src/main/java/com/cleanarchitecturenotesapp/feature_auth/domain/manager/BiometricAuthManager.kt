package com.cleanarchitecturenotesapp.feature_auth.domain.manager

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.cleanarchitecturenotesapp.feature_auth.domain.manager.BiometricAuthResult.Cancelled
import com.cleanarchitecturenotesapp.feature_auth.domain.manager.BiometricAuthResult.Error
import com.cleanarchitecturenotesapp.feature_auth.domain.manager.BiometricAuthResult.Failed
import com.cleanarchitecturenotesapp.feature_auth.domain.manager.BiometricAuthResult.Success
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sealed class representing the result of biometric authentication.
 *
 * @property Success Indicates successful authentication
 * @property Error Indicates an error occurred during authentication
 * @property Failed Indicates authentication failed (wrong biometric)
 * @property Cancelled Indicates user cancelled the authentication
 */
sealed class BiometricAuthResult {
    data object Success : BiometricAuthResult()
    data class Error(val errorCode: Int, val errorMessage: String) : BiometricAuthResult()
    data class Failed(val errorMessage: String) : BiometricAuthResult()
    data object Cancelled : BiometricAuthResult()
}

/**
 * Manager class responsible for handling biometric authentication operations.
 *
 * This manager provides a clean interface for:
 * - Checking biometric availability on the device
 * - Authenticating users using biometric credentials (fingerprint, face, etc.)
 * - Handling various authentication states and errors
 *
 * @param context Application context for accessing biometric services
 *
 * @see BiometricManager
 * @see BiometricPrompt
 */
@Singleton
class BiometricAuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val allowedAuthenticators =
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL

    /**
     * Checks if biometric authentication is available on the device.
     *
     * @return [BiometricAuthResult] indicating availability:
     * - [BiometricAuthResult.Success] if biometric is available and ready
     * - [BiometricAuthResult.Error] if biometric is not available or not set up
     */
    fun isBiometricAvailable(): BiometricAuthResult {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(allowedAuthenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> BiometricAuthResult.Success
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                BiometricAuthResult.Error(
                    BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
                    "No biometric hardware available on this device"
                )
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                BiometricAuthResult.Error(
                    BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
                    "Biometric hardware is currently unavailable"
                )
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                BiometricAuthResult.Error(
                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED,
                    "No biometric credentials enrolled. Please set up biometric authentication in device settings"
                )
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                BiometricAuthResult.Error(
                    BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED,
                    "Security update required for biometric authentication"
                )
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                BiometricAuthResult.Error(
                    BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED,
                    "Biometric authentication is not supported on this device"
                )
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                BiometricAuthResult.Error(
                    BiometricManager.BIOMETRIC_STATUS_UNKNOWN,
                    "Unable to determine biometric status"
                )
            }
            else -> {
                BiometricAuthResult.Error(
                    -1,
                    "Unknown biometric error"
                )
            }
        }
    }

    /**
     * Authenticates the user using biometric credentials.
     *
     * This method displays the system biometric prompt and handles the authentication flow.
     * The result is delivered through the provided callback.
     *
     * @param activity The FragmentActivity hosting the biometric prompt
     * @param title Title text displayed on the biometric prompt dialog
     * @param subtitle Subtitle text displayed on the biometric prompt dialog (optional)
     * @param description Description text displayed on the biometric prompt dialog (optional)
     * @param onResult Callback invoked with the authentication result
     *
     * @see BiometricPrompt
     */
    fun authenticate(
        activity: FragmentActivity,
        title: String = "Biometric Authentication",
        subtitle: String? = null,
        description: String? = null,
        onResult: (BiometricAuthResult) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onResult(BiometricAuthResult.Success)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    when (errorCode) {
                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                            onResult(BiometricAuthResult.Cancelled)
                        }
                        else -> {
                            onResult(BiometricAuthResult.Error(errorCode, errString.toString()))
                        }
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onResult(BiometricAuthResult.Failed("Authentication failed. Please try again."))
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .apply {
                subtitle?.let { setSubtitle(it) }
                description?.let { setDescription(it) }
            }
            .setAllowedAuthenticators(allowedAuthenticators)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

