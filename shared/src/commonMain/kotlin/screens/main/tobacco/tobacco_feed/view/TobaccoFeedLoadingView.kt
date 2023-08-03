package screens.main.tobacco.tobacco_feed.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.KalyanTheme
import ui.components.KalyanCircularProgress

@Composable
fun TobaccoFeedLoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().background(KalyanTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        KalyanCircularProgress(color = KalyanTheme.colors.primary)
    }
}
