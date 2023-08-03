package ui.components.android

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ui.KalyanTheme

@Composable
internal fun AndroidDivider(
    thickness: Dp,
    modifier: Modifier = Modifier
) {
    Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = thickness,
        color = KalyanTheme.colors.surfaceVariant
    )
}
