package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.MRTheme

@Composable
fun MRToolbar(
    title: String = "",
    isTransparent: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onFirstIconClick: (() -> Unit)? = null
) {
    TopAppBar(
        backgroundColor = if (isTransparent) Color.Transparent else MRTheme.colors.background,
        elevation = if (isTransparent) 0.dp else 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            onBackClick?.let {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    colorFilter = ColorFilter.tint(MRTheme.colors.backgroundOn),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp).align(Alignment.CenterVertically).clickable {
                        onBackClick.invoke()
                    }
                )
            }

            Text(
                text = title,
                style = MRTheme.typography.header,
                color = MRTheme.colors.backgroundOn,
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                textAlign = TextAlign.Start
            )

            onFirstIconClick?.let {
                Image(
                    imageVector = Icons.Default.Settings,
                    colorFilter = ColorFilter.tint(MRTheme.colors.backgroundOn),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 16.dp).clickable {
                        onFirstIconClick.invoke()
                    }
                )
            }
        }
    }
}
