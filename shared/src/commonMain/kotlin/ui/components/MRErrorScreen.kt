package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.my_rating.shared.strings.AppResStrings
import ui.MRTheme
import utils.clickableRipple

@Composable
fun MRErrorScreen(modifier: Modifier = Modifier, onRepeatClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = "Error",
                colorFilter = ColorFilter.tint(MRTheme.colors.error.copy(alpha = 0.8f)),
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = AppResStrings.error_something_went_wrong_screen_title,
                style = MRTheme.typography.header
            )

            Row() {
                Text(
                    text = AppResStrings.error_something_went_wrong_screen_hint,
                    style = MRTheme.typography.body,
                    color = MRTheme.colors.secondaryText
                )

                Text(
                    text = AppResStrings.text_refresh,
                    color = MRTheme.colors.primary,
                    style = MRTheme.typography.body,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .clickableRipple {
                            onRepeatClick()
                        }
                )
            }
        }
    }
}
