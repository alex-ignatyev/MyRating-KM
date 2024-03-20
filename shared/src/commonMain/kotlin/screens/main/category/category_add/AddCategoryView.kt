package screens.main.category.category_add

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.main.category.category_add.AddCategoryAction.AddCategory
import screens.main.category.category_add.AddCategoryAction.OnCategoryChange
import screens.main.category.category_add.AddCategoryAction.OnCategoryIconChange
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close
import ui.getIcons
import utils.clickableRipple
import utils.keyboardAsState

@Composable
fun AddCategoryView(
    state: AddCategoryState,
    rootModifier: Modifier = Modifier,
    doAction: (AddCategoryAction) -> Unit
) {

    val isKeyboardOpen by keyboardAsState()
    val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else rootModifier

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            MRToolbar(
                title = AppResStrings.title_add_category,
                backIcon = Close,
                onBackClick = {
                    doAction.invoke(AddCategoryAction.OnBackClick)
                })
        }
    ) {
        Box(
            modifier = Modifier
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
                    placeholder = AppResStrings.text_categories_name
                ) {
                    doAction(OnCategoryChange(it))
                }

                Text(
                    text = "Category Icon",
                    style = MRTheme.typography.hint,
                    modifier = Modifier.padding(top = 16.dp, start = 46.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.FixedSize(size = 40.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                ) {
                    itemsIndexed(getIcons()) { index, image ->
                        Card(
                            backgroundColor = if (index == state.icon) MRTheme.colors.primary else MRTheme.colors.background,
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
                modifier = buttonModifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                text = if (state.isLoading) null else AppRes.string.title_add_category,
                enabled = !state.isLoading,
                content = {
                    MRCircularProgress()
                },
                onClick = {
                    doAction(AddCategory)
                }
            )
        }
    }
}
