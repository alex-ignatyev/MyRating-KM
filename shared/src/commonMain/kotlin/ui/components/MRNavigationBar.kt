package ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.contentColorFor
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import di.LocalPlatform
import di.Platform.iOS
import di.currentPlatform
import kotlin.math.ln
import ui.MRTheme
import ui.components.android.AndroidNavigationBar
import ui.components.ios.IosNavigationBar

@Composable
fun MRNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MRTheme.colors.background,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.AdaptiveElevation,
    isTransparent: () -> Boolean = { false },
    onFloatingAction: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    when (LocalPlatform.current) {
        iOS -> IosNavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            isTransparent = isTransparent,
            content = content
        )

        else -> AndroidNavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            content = content
        )
    }
}

interface Tab {
    val index: Int
    val title: String
    val icon: ImageVector
}

@Composable
internal fun RowScope.TabNavigationItem(tab: Tab, selectedIndexTab: Int, onClick: () -> Unit) {
    BottomNavigationItem(
        selected = selectedIndexTab == tab.index,
        onClick = { onClick.invoke() },
        icon = {
            val painterIcon = rememberVectorPainter(tab.icon)
            Icon(painter = painterIcon, contentDescription = tab.title)
        },
        selectedContentColor = MRTheme.colors.primary,
        unselectedContentColor = MRTheme.colors.surfaceVariantOn
    )
}

val NavigationBarDefaults.AdaptiveElevation
    @Composable get() = when (currentPlatform) {
        iOS -> 0.dp
        else -> Elevation
    }

@Composable
fun color(): Color {
    val elevation = 5.dp
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    return MRTheme.colors.primary.copy(alpha = alpha).compositeOver(MRTheme.colors.background)
}
