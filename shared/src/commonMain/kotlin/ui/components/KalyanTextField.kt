package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ui.KalyanTheme
import ui.components.TextFieldType.Password

@Composable
fun KalyanTextField(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    fieldType: TextFieldType = TextFieldType.Text,
    inputType: KeyboardType = KeyboardType.Text,
    endIcon: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth().height(74.dp).padding(top = 16.dp, start = 32.dp, end = 32.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        enabled = enabled,
        label = { Text(text = placeholder, color = KalyanTheme.colors.secondaryText) },
        visualTransformation = if (fieldType is Password && fieldType.passwordState) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        trailingIcon = endIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = KalyanTheme.colors.primary,
            unfocusedBorderColor = KalyanTheme.colors.surfaceVariant,
            disabledBorderColor = KalyanTheme.colors.surfaceVariant,
            errorBorderColor = KalyanTheme.colors.error,
            textColor = KalyanTheme.colors.backgroundOn,
            cursorColor = KalyanTheme.colors.primary
        )
    )
}

sealed interface TextFieldType {
    object Text : TextFieldType
    data class Password(val passwordState: Boolean) : TextFieldType
}
