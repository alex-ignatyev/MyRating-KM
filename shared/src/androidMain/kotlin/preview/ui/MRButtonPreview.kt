package preview.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.MRTheme
import ui.components.MRButton

@Preview
@Composable
fun MRButtonPreview_Light() {
    MRTheme(darkTheme = false) {
        MRButton(text = "Нажать") { }
    }
}

@Preview
@Composable
fun MRButtonPreview_Dark() {
    MRTheme(darkTheme = true) {
        MRButton(text = "Нажать") { }
    }
}
