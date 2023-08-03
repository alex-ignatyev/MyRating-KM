package screens.main.mix.mix_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.my_rating.shared.strings.AppResStrings
import com.moriatsushi.insetsx.safeArea
import ui.KalyanTheme

@Composable
internal fun MixFeedView(viewState: MixFeedState, obtainEvent: (MixFeedEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.safeArea)
    ) {
        Text(
            text = AppResStrings.title_mix,
            style = KalyanTheme.typography.header
        )
    }
}
