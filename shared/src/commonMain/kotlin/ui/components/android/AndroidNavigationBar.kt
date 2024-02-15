package ui.components.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.contentColorFor
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.navigationBars
import ui.KalyanTheme
import ui.components.ios.IosDivider

@Composable
fun AndroidNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = KalyanTheme.colors.primary,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable RowScope.() -> Unit
) {
    Column(Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
        IosDivider(thickness = 1.5.dp)
        BottomAppBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            actions = { content() }
        )
    }
}
