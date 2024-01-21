package screens.main.feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.my_rating.shared.images.AppResImages
import com.my_rating.shared.strings.AppResStrings
import io.github.skeptick.libres.compose.painterResource
import screens.main.feed.FeedEvent.OnAddRequest
import ui.KalyanTheme
import ui.components.KalyanButton
import utils.mvi.Event

@Composable
fun FeedEmptyView(obtainEvent: (Event) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(AppResImages.ic_empty),
                null,
                colorFilter = ColorFilter.tint(KalyanTheme.colors.surfaceVariant),
                modifier = Modifier.size(128.dp)
            )
            Text(text = AppResStrings.text_empty_list, style = KalyanTheme.typography.header)
            Text(
                text = AppResStrings.text_cant_find,
                style = KalyanTheme.typography.hint,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            KalyanButton(text = AppResStrings.title_create_request, modifier = Modifier.padding(top = 32.dp)) {
                obtainEvent(OnAddRequest())
            }
        }
    }
}
