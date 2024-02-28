package screens.main.category.category_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.strings.AppResStrings
import screens.main.category.category_feed.CategoryFeedAction.AddCategory
import screens.main.category.category_feed.CategoryFeedAction.InitCategoryFeedScreen
import screens.main.category.category_feed.CategoryFeedAction.OnErrorRefresh
import ui.MRTheme
import ui.components.ErrorScreen
import ui.components.MREmptyScreen
import ui.components.MRLoadingScreen
import ui.components.MRToolbar

@Composable
fun CategoryFeedScreen(component: CategoryFeedComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()
    val screenModifier = Modifier.background(MRTheme.colors.background)

    Column(modifier = screenModifier.windowInsetsPadding(WindowInsets.statusBars)) {
        MRToolbar(
            title = AppResStrings.title_categories,
            modifier = screenModifier,
            onRightIconActionClick = {
                component.doAction(AddCategory)
            })

        when {
            state.isLoading -> MRLoadingScreen()
            state.error.isNotEmpty() -> ErrorScreen(modifier) { component.doAction(OnErrorRefresh) }
            state.data.isEmpty() -> MREmptyScreen(
                title = AppResStrings.title_categories_empty,
                description = AppResStrings.text_categories_cant_find,
                modifier = modifier
            )

            else -> CategoryFeedView(state.data, Modifier) { component.doAction(it) }
        }
    }

    LaunchedEffect(Unit) {
        component.doAction(InitCategoryFeedScreen)
    }
}
