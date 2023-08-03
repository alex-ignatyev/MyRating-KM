package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.my_rating.shared.images.AppResImages
import io.github.skeptick.libres.compose.painterResource
import ui.KalyanTheme

@Composable
fun KalyanSelect(title: String, text: String = "", modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth().height(74.dp).padding(horizontal = 32.dp).padding(top = 16.dp).clickable {
        onClick.invoke()
    }) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = title,
                style = if (text.isBlank()) KalyanTheme.typography.body else KalyanTheme.typography.hint,
                color = KalyanTheme.colors.outline,
                modifier = Modifier.padding(start = 16.dp)
            )

            if (text.isNotBlank()) {
                Text(
                    text = text,
                    style = KalyanTheme.typography.body,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Image(
            painter = painterResource(AppResImages.ic_next),
            contentDescription = null,
            colorFilter = ColorFilter.tint(KalyanTheme.colors.outline),
            modifier = Modifier.size(16.dp).align(Alignment.CenterEnd)
        )

        KalyanDivider(modifier = modifier.padding(top = 8.dp).align(Alignment.BottomCenter))
    }
}
