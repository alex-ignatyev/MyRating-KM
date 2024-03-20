package screens.main.category.caategory_edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells.FixedSize
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my_rating.shared.strings.AppResStrings
import screens.main.category.caategory_edit.EditCategoryAction.ChangeCategory
import screens.main.category.caategory_edit.EditCategoryAction.OnCategoryIconChange
import screens.main.category.caategory_edit.EditCategoryAction.OnCategoryTitleChange
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close
import ui.getIcons
import utils.clickableRipple
import utils.keyboardAsState

@Composable
fun EditCategoryView(
    state: EditCategoryState,
    rootModifier: Modifier = Modifier,
    doAction: (EditCategoryAction) -> Unit
) {

    val isKeyboardOpen by keyboardAsState()
    val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else rootModifier

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            MRToolbar(
                title = AppResStrings.title_edit_category,
                backIcon = Close,
                onBackClick = {
                    doAction.invoke(EditCategoryAction.OnBackClick)
                })
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .background(MRTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                MRTextField(
                    value = state.title,
                    placeholder = AppResStrings.text_categories_name,
                    onValueChange = { title ->
                        doAction(OnCategoryTitleChange(title))
                    }
                )

                Text(
                    text = "Category Icon",
                    style = MRTheme.typography.hint,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 48.dp)
                )

                LazyVerticalGrid(
                    columns = FixedSize(size = 40.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                ) {
                    itemsIndexed(getIcons()) { index, image ->
                        Card(
                            colors = cardColors(containerColor = if (index == state.icon) MRTheme.colors.primary else MRTheme.colors.background),
                            shape = RoundedCornerShape(2.dp),
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .padding(4.dp)
                                .clickableRipple {
                                    doAction(OnCategoryIconChange(index))
                                }
                        ) {
                            Image(imageVector = image, contentDescription = null)
                        }
                    }
                }
                MRTextError(errorText = state.error)
            }

            MRButton(
                text = AppResStrings.change_category,
                modifier = buttonModifier.align(Alignment.BottomCenter),
                onClick = {
                    doAction(ChangeCategory)
                }
            )
        }
    }
}
