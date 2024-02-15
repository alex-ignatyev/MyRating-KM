package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class MRColors(
    val primary: Color,
    val primaryOn: Color,
    val primaryContainer: Color,
    val primaryContainerOn: Color,

    val secondary: Color,
    val secondaryOn: Color,
    val secondaryContainer: Color,
    val secondaryContainerOn: Color,

    val tertiary: Color,
    val tertiaryOn: Color,
    val tertiaryContainer: Color,
    val tertiaryContainerOn: Color,

    val error: Color,
    val errorOn: Color,
    val errorContainer: Color,
    val errorContainerOn: Color,

    val background: Color,
    val backgroundOn: Color,

    val surface: Color,
    val surfaceOn: Color,

    val surfaceVariant: Color,
    val surfaceVariantOn: Color,

    val outline: Color,

    val primaryText: Color,
    val secondaryText: Color
)

data class MRTypography(
    val header: TextStyle,
    val toolbar: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
    val hint: TextStyle
)

object MRTheme {
    internal val colors: MRColors
        @Composable get() = LocalMRColors.current

    internal val typography: MRTypography
        @Composable get() = LocalMRTypography.current
}

internal val LocalMRColors = staticCompositionLocalOf<MRColors> { error("No colors provided") }
internal val LocalMRTypography = staticCompositionLocalOf<MRTypography> { error("No font provided") }
