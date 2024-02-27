package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.my_rating.shared.images.AppResImages
import com.my_rating.shared.strings.AppResStrings
import io.github.skeptick.libres.compose.painterResource
import ui.MRTheme

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onRepeatClick: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(AppResImages.ic_error),
                contentDescription = "Error",
                colorFilter = ColorFilter.tint(MRTheme.colors.error),
                modifier = Modifier.size(128.dp)
            )

            Text(
                text = AppResStrings.error_something_went_wrong_screen_title,
                style = MRTheme.typography.header
            )

            Text(
                text = AppResStrings.error_something_went_wrong_screen_hint,
                style = MRTheme.typography.hint,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            MRButton(
                text = AppResStrings.text_repeat,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                onRepeatClick()
            }
        }
    }
}
