package ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.unit.sp

@Composable
internal fun MRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) paletteDark else paletteLight

    val typography = MRTypography(
        header = TextStyle(
            color = colors.primaryText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = Companion.Center
        ),
        toolbar = TextStyle(
            color = colors.primaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = Companion.Center
        ),
        body = TextStyle(
            color = colors.primaryText,
            fontSize = 16.sp,
            textAlign = Companion.Center
        ),
        caption = TextStyle(
            color = colors.primaryText,
            fontSize = 14.sp,
            textAlign = Companion.Center
        ),
        hint = TextStyle(
            color = colors.secondaryText,
            fontSize = 12.sp,
            textAlign = Companion.Center
        )
    )

    CompositionLocalProvider(
        LocalMRColors provides colors,
        LocalMRTypography provides typography,
        content = content
    )
}
