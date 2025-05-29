package com.cleanarchitecturenotesapp.ui.theme.appComponents.appTextField

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Sealed class representing all possible parameter configurations for the AppTextField component.
 * This ensures type-safety and encapsulation of textField parameters.
 */
sealed class AppTextFieldParams {
    /**
     * Data class containing all configuration parameters for an AppTextField.
     *
     * @property value Current text value of the field
     * @property onValueChange Callback invoked when the text value changes
     * @property modifier [Modifier] to be applied to the text field
     * @property enabled Controls whether the text field is enabled for user input
     * @property readOnly If true, text field becomes read-only but remains selectable
     * @property isError Whether the text field should display an error state
     * @property isOutlined Whether to use outlined style instead of filled style
     * @property errorMessage Error message to display when [isError] is true
     * @property textStyle Style configuration for the input text
     * @property label Optional label displayed above the text field
     * @property placeholder Optional placeholder shown when the field is empty
     * @property leadingIcon Optional icon displayed at the start of the text field
     * @property trailingIcon Optional icon displayed at the end of the text field
     * @property prefix Optional prefix text/icon inside the text field
     * @property suffix Optional suffix text/icon inside the text field
     * @property supportingText Optional helper text displayed below the text field
     * @property visualTransformation Transforms the visual representation of the input
     * @property keyboardOptions Configuration for the software keyboard
     * @property keyboardActions Handling of keyboard actions (e.g., IME actions)
     * @property singleLine Whether the text field should be single line
     * @property maxLines Maximum number of lines for the text field
     * @property minLines Minimum number of lines for the text field
     * @property interactionSource Handles different interaction states
     * @property shape Shape of the text field container
     * @property colors Color scheme for different parts of the text field
     */
    @Stable
    data class TextFieldParams(
        val value: String,
        val onValueChange: (String) -> Unit,
        val modifier: Modifier,
        val enabled: Boolean,
        val readOnly: Boolean,
        val isError: Boolean,
        val isOutlined: Boolean,
        val errorMessage: String,
        val textStyle: TextStyle,
        val label: @Composable (() -> Unit)?,
        val placeholder: @Composable (() -> Unit)?,
        val leadingIcon: @Composable (() -> Unit)?,
        val trailingIcon: @Composable (() -> Unit)?,
        val prefix: @Composable (() -> Unit)?,
        val suffix: @Composable (() -> Unit)?,
        val supportingText: @Composable (() -> Unit)?,
        val visualTransformation: VisualTransformation,
        val keyboardOptions: KeyboardOptions,
        val keyboardActions: KeyboardActions,
        val singleLine: Boolean,
        val maxLines: Int,
        val minLines: Int,
        val interactionSource: MutableInteractionSource,
        val shape: Shape,
        val colors: TextFieldColors
    ) : AppTextFieldParams()
} 