package ui.components.ios

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.KalyanTheme

@Composable
@NonRestartableComposable
internal fun IosDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = .5.dp,
    color: Color = KalyanTheme.colors.surfaceVariant.copy(alpha = .25f)
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
