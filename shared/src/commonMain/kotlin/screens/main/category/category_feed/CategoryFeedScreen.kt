package screens.main.category.category_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.strings.AppResStrings
import screens.main.category.category_feed.CategoryFeedAction.AddCategory
import screens.main.category.category_feed.CategoryFeedAction.EditCategory
import screens.main.category.category_feed.CategoryFeedAction.InitCategoryFeedScreen
import screens.main.category.category_feed.CategoryFeedAction.OnErrorRefresh
import ui.MRTheme
import ui.components.MREmptyScreen
import ui.components.MRErrorScreen
import ui.components.MRLoadingScreen
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close

@Composable
fun CategoryFeedScreen(component: CategoryFeedComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()
    val screenModifier = Modifier.background(MRTheme.colors.background)

    Column(modifier = screenModifier.windowInsetsPadding(WindowInsets.statusBars)) {
        MRToolbar(
            title = if (state.isEdit) AppResStrings.title_categories_edit else AppResStrings.title_categories,
            modifier = screenModifier,
            backIcon = Close,
            onBackClick = if (state.isEdit) {
                { component.doAction(EditCategory) }
            } else null,
            onLeftIconActionClick = if (state.isEdit) null else {
                { component.doAction(EditCategory) }
            },
            onRightIconActionClick = if (state.isEdit) null else {
                { component.doAction(AddCategory) }
            }
        )

        when {
            state.isLoading -> MRLoadingScreen()
            state.error.isNotEmpty() -> MRErrorScreen(modifier) { component.doAction(OnErrorRefresh) }
            state.data.isEmpty() -> MREmptyScreen(
                title = AppResStrings.title_categories_empty,
                description = AppResStrings.text_categories_cant_find,
                modifier = modifier
            )

            else -> CategoryFeedView(state, Modifier) { component.doAction(it) }
        }
    }

    LaunchedEffect(Unit) {
        component.doAction(InitCategoryFeedScreen)
    }
}
