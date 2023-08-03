package ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) paletteDark else paletteLight

    //TODO Добавить text align а в других местах убрать
    val typography = KalyanTypography(
        header = TextStyle(
            color = colors.primaryText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        toolbar = TextStyle(
            color = colors.primaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        body = TextStyle(
            color = colors.primaryText,
            fontSize = 16.sp
        ),
        caption = TextStyle(
            color = colors.primaryText,
            fontSize = 14.sp
        ),
        hint = TextStyle(
            color = colors.secondaryText,
            fontSize = 12.sp
        )
    )

    CompositionLocalProvider(
        LocalKalyanColors provides colors,
        LocalKalyanTypography provides typography,
        content = content
    )
}
