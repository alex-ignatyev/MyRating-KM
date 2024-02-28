package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.MRTheme

@Composable
fun MRLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(MRTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        MRCircularProgress(color = MRTheme.colors.primary)
    }
}
