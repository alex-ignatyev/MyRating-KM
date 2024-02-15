package screens.main.category.category_add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.main.category.category_add.AddCategoryAction.AddCategory
import screens.main.category.category_add.AddCategoryAction.OnBackClick
import screens.main.category.category_add.AddCategoryAction.OnCategoryChange
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanTextField
import utils.keyboardAsState

@Composable
fun AddCategoryView(
    state: AddCategoryState,
    modifier: Modifier = Modifier,
    doAction: (AddCategoryAction) -> Unit
) {

    val isKeyboardOpen by keyboardAsState()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Toolbar(doAction)
        }
    ) {
        val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else Modifier.padding(bottom = 100.dp)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(KalyanTheme.colors.background)
        ) {
            KalyanTextField(
                value = state.title,
                placeholder = AppResStrings.text_categories_name
            ) {
                doAction(OnCategoryChange(it))
            }

            KalyanButton(
                modifier = buttonModifier.padding(vertical = 16.dp).align(Alignment.BottomCenter),
                text = if (state.isLoading) null else AppRes.string.title_add_category,
                enabled = !state.isLoading && state.isButtonEnabled,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    doAction(AddCategory)
                }
            )
        }
    }
}

@Composable
fun Toolbar(doAction: (AddCategoryAction) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KalyanTheme.colors.background)
            .padding(top = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .clickable {
                    doAction(OnBackClick)
                },
            contentDescription = null
        )
        Text(
            text = AppResStrings.title_add_category,
            style = KalyanTheme.typography.header,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
