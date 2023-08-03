package ui.components.ios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.contentColorFor
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.navigationBars
import ui.KalyanTheme
import ui.components.AdaptiveElevation

@Composable
@NonRestartableComposable
fun IosNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = KalyanTheme.colors.primary,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.AdaptiveElevation,
    isTransparent: () -> Boolean = { false },
    withDivider: Boolean = !isTransparent(),
    content: @Composable RowScope.() -> Unit
) {
    val background by remember {
        derivedStateOf {
            if (isTransparent())
                containerColor.copy(alpha = 0f) else containerColor
        }
    }

    Surface(
        modifier = modifier,
        color = background,
        tonalElevation = tonalElevation,
        contentColor = contentColor,
    ) {
        Column(Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
            if (withDivider) {
                IosDivider()
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(CupertinoNavigationBarDefaults.height)
                    .padding(top = 2.dp)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
        }
    }
}

object CupertinoNavigationBarDefaults {
    val height = 50.dp
}
