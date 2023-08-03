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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import di.LocalPlatform
import di.Platform.iOS
import di.currentPlatform
import kotlin.math.ln
import ui.KalyanTheme
import ui.components.android.AndroidNavigationBar
import ui.components.ios.IosNavigationBar

// From: https://github.com/alexzhirkevich/compose-look-and-feel

@Composable
fun KalyanNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = KalyanTheme.colors.background,
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
            onFloatingAction = onFloatingAction,
            content = content
        )
    }
}

@Composable
internal fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title) } },
        selectedContentColor = KalyanTheme.colors.primary,
        unselectedContentColor = KalyanTheme.colors.surfaceVariantOn
    )
}

/*
@Composable
fun RowScope.AdaptiveNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.adaptiveColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    when (LocalPlatform.current) {
        iOS -> CupertinoNavigationBarItem(
            selected = selected,
            onClick = onClick,
            icon = icon,
            modifier = modifier,
            enabled = enabled,
            label = label,
            alwaysShowLabel = alwaysShowLabel,
            colors = colors,
            interactionSource = interactionSource
        )

        else -> NavigationBarItem(
            selected = selected,
            onClick = onClick,
            icon = icon,
            modifier = modifier,
            enabled = enabled,
            label = label,
            alwaysShowLabel = alwaysShowLabel,
            colors = colors,
            interactionSource = interactionSource
        )
    }
}
*/

val NavigationBarDefaults.AdaptiveElevation
    @Composable get() = when (currentPlatform) {
        iOS -> 0.dp
        else -> Elevation
    }


///// --------------------


/*
@Composable
fun NavigationBarItemDefaults.cupertinoColors(
    selectedIconColor: Color = KalyanTheme.colors.generalColor,
    selectedTextColor: Color = KalyanTheme.colors.generalColor,
    unselectedIconColor: Color = KalyanTheme.colors.secondaryText,
    unselectedTextColor: Color = KalyanTheme.colors.secondaryText,
) = colors(
    selectedIconColor = selectedIconColor,
    selectedTextColor = selectedTextColor,
    unselectedIconColor = unselectedIconColor,
    unselectedTextColor = unselectedTextColor
)
*/

/*
@Composable
fun RowScope.CupertinoNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.cupertinoColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    val pressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier
            .graphicsLayer {
                alpha = if (!selected && pressed) .5f else 1f
            }
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null
            )
            .weight(1f)
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompositionLocalProvider(
            //LocalContentColor provides colors.iconColor(selected = selected).value
        ) {
            icon()
        }
        if (label != null && (alwaysShowLabel || selected)) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.labelSmall.copy(
                    // color = colors.textColor(selected = selected).value
                )
            ) {
                label()
            }
        }
    }
}
*/

@Composable
fun color(): Color {
    val elevation = 5.dp
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    return KalyanTheme.colors.primary.copy(alpha = alpha).compositeOver(KalyanTheme.colors.background)
}
