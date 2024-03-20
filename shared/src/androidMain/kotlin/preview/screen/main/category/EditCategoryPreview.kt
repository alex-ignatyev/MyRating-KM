package preview.screen.main.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import screens.main.category.caategory_edit.EditCategoryState
import screens.main.category.caategory_edit.EditCategoryView
import ui.MRTheme

@Preview
@Composable
fun EditCategoryPreview_Light() {
    MRTheme(darkTheme = false) {
        EditCategoryView(state = EditCategoryState(), doAction = {})
    }
}
