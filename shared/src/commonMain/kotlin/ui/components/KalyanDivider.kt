package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import di.Platform.iOS
import di.currentPlatform
import ui.components.android.AndroidDivider
import ui.components.ios.IosDivider

@Composable
internal fun KalyanDivider(thickness: Dp = 1.dp, modifier: Modifier = Modifier) {
    when (currentPlatform) {
        iOS -> IosDivider(modifier)
        else -> AndroidDivider(thickness, modifier)
    }
}
