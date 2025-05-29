package com.cleanarchitecturenotesapp.ui.theme.appComponents.appTextField

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Creates and remembers a [AppTextFieldParams.TextFieldParams] instance with the provided configuration.
 * This composable function helps in managing text field parameters efficiently by remembering the configuration
 * across recompositions.
 *
 * Example usage:
 * ```
 * var text by remember { mutableStateOf("") }
 * AppTextField(
 *     params = rememberAppTextFieldParams(
 *         value = text,
 *         onValueChange = { text = it },
 *         label = { Text("Label") }
 *     )
 * )
 * ```
 *
 * @param value Current text value of the field
 * @param onValueChange Callback invoked when the text value changes
 * @param modifier [Modifier] to be applied to the text field
 * @param enabled Controls whether the text field is enabled for user input
 * @param readOnly If true, text field becomes read-only but remains selectable
 * @param isError Whether the text field should display an error state
 * @param isOutlined Whether to use outlined style instead of filled style
 * @param errorMessage Error message to display when [isError] is true
 * @param textStyle Style configuration for the input text
 * @param label Optional label displayed above the text field
 * @param placeholder Optional placeholder shown when the field is empty
 * @param leadingIcon Optional icon displayed at the start of the text field
 * @param trailingIcon Optional icon displayed at the end of the text field
 * @param prefix Optional prefix text/icon inside the text field
 * @param suffix Optional suffix text/icon inside the text field
 * @param supportingText Optional helper text displayed below the text field
 * @param visualTransformation Transforms the visual representation of the input
 * @param keyboardOptions Configuration for the software keyboard
 * @param keyboardActions Handling of keyboard actions (e.g., IME actions)
 * @param singleLine Whether the text field should be single line
 * @param maxLines Maximum number of lines for the text field
 * @param minLines Minimum number of lines for the text field
 * @param interactionSource Handles different interaction states
 * @param shape Shape of the text field container
 * @param colors Color scheme for different parts of the text field
 *
 * @return A remembered instance of [AppTextFieldParams.TextFieldParams] with the specified configuration
 */
@Composable
fun rememberAppTextFieldParams(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    isOutlined: Boolean = false,
    errorMessage: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = if (isOutlined) OutlinedTextFieldDefaults.shape else TextFieldDefaults.shape,
    colors: TextFieldColors = if (isOutlined) {
        OutlinedTextFieldDefaults.colors()
    } else {
        TextFieldDefaults.colors()
    }
) = AppTextFieldParams.TextFieldParams(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    enabled = enabled,
    readOnly = readOnly,
    isError = isError,
    isOutlined = isOutlined,
    errorMessage = errorMessage,
    textStyle = textStyle,
    label = label,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    prefix = prefix,
    suffix = suffix,
    supportingText = supportingText,
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    maxLines = maxLines,
    minLines = minLines,
    interactionSource = interactionSource,
    shape = shape,
    colors = colors
) 