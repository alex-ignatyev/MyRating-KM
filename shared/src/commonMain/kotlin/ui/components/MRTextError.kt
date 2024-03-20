package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.MRTheme

@Composable
fun MRTextError(errorText: String, modifier: Modifier = Modifier) {
    Text(
        text = errorText,
        style = MRTheme.typography.body,
        color = MRTheme.colors.error,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}
