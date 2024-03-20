package preview.screen.main.product

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import screens.main.product.product_edit.EditProductState
import screens.main.product.product_edit.EditProductView
import ui.MRTheme

@Preview
@Composable
fun EditProductPreview_Light() {
    MRTheme(darkTheme = false) {
        EditProductView(state = EditProductState(), doAction = {})
    }
}
