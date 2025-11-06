package com.cleanarchitecturenotesapp.ui.theme.appComponents.appTextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Custom text field component that supports both outlined and filled styles.
 * Wraps Material3 TextField and OutlinedTextField with unified parameters.
 *
 * @param params Configuration parameters for the text field
 */
@Composable
fun AppTextField(
    params: AppTextFieldParams.TextFieldParams
) {
    Column {
        if (params.isOutlined) {
            OutlinedTextField(
                value = params.value,
                onValueChange = params.onValueChange,
                modifier = params.modifier,
                enabled = params.enabled,
                readOnly = params.readOnly,
                textStyle = params.textStyle,
                label = params.label,
                placeholder = params.placeholder,
                leadingIcon = params.leadingIcon,
                trailingIcon = params.trailingIcon,
                prefix = params.prefix,
                suffix = params.suffix,
                supportingText = params.supportingText,
                isError = params.isError,
                visualTransformation = params.visualTransformation,
                keyboardOptions = params.keyboardOptions,
                keyboardActions = params.keyboardActions,
                singleLine = params.singleLine,
                maxLines = params.maxLines,
                minLines = params.minLines,
                interactionSource = params.interactionSource,
                shape = params.shape,
                colors = params.colors
            )
        } else {
            TextField(
                value = params.value,
                onValueChange = params.onValueChange,
                modifier = params.modifier,
                enabled = params.enabled,
                readOnly = params.readOnly,
                textStyle = params.textStyle,
                label = params.label,
                placeholder = params.placeholder,
                leadingIcon = params.leadingIcon,
                trailingIcon = params.trailingIcon,
                prefix = params.prefix,
                suffix = params.suffix,
                supportingText = params.supportingText,
                isError = params.isError,
                visualTransformation = params.visualTransformation,
                keyboardOptions = params.keyboardOptions,
                keyboardActions = params.keyboardActions,
                singleLine = params.singleLine,
                maxLines = params.maxLines,
                minLines = params.minLines,
                interactionSource = params.interactionSource,
                shape = params.shape,
                colors = params.colors
            )
        }

        if (params.isError && params.errorMessage.isNotEmpty()) {
            Text(
                text = params.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    top = 10.dp
                )
            )
        }
    }
}