package screens.main.feed.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.MRTheme
import ui.components.MRCircularProgress

@Composable
fun FeedLoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().background(MRTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        MRCircularProgress(color = MRTheme.colors.primary)
    }
}
