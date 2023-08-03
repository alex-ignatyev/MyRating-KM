package screens.main.admin.admin_feed

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
internal fun AdminFeedView(viewState: AdminFeedState, obtainEvent: (AdminFeedEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.safeArea)
    ) {
        Text(
            text = AppResStrings.title_admin,
            style = KalyanTheme.typography.header,
            color = KalyanTheme.colors.backgroundOn
        )
    }
}
