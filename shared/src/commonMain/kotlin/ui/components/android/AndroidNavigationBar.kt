package ui.components.android

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.KalyanTheme

@Composable
fun AndroidNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = KalyanTheme.colors.primary,
    contentColor: Color = contentColorFor(containerColor),
    onFloatingAction: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        actions = { content() },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(start = 48.dp),
                onClick = onFloatingAction,
                containerColor = KalyanTheme.colors.primary
            ) {
                Icon(imageVector = Filled.Add, tint = KalyanTheme.colors.background, contentDescription = "Add mix")
            }
        }
    )
}
