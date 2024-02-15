package screens.main.feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import ui.KalyanTheme

@Composable
fun FeedEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(AppResImages.ic_empty),
            null,
            colorFilter = ColorFilter.tint(KalyanTheme.colors.surfaceVariant),
            modifier = Modifier.size(128.dp)
        )
        Text(text = AppResStrings.title_categories_empty, style = KalyanTheme.typography.header)
        Text(
            text = AppResStrings.text_categories_cant_find,
            style = KalyanTheme.typography.hint,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}
