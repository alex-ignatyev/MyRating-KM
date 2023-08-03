package ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ui.KalyanTheme

@Composable
fun PasswordShowIcon(isPasswordHidden: Boolean, onClick: () -> Unit) {
    Icon(
        imageVector = if (isPasswordHidden) {
            Icons.Default.Visibility
        } else {
            Icons.Default.VisibilityOff
        },
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick()
        },
        contentDescription = null,
        tint = KalyanTheme.colors.surfaceVariant
    )
}
