package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import ui.MRTheme

@Composable
fun MREmptyScreen(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(AppResImages.ic_empty),
            null,
            colorFilter = ColorFilter.tint(MRTheme.colors.surfaceVariant),
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = title,
            style = MRTheme.typography.header
        )
        Text(
            text = description,
            style = MRTheme.typography.hint,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}
