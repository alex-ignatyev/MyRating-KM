package navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.main.category.CategoryNavigation
import screens.main.category.CategoryNavigation.CategoryScreen.AddCategory
import screens.main.category.CategoryNavigation.CategoryScreen.CategoryFeed
import screens.main.category.CategoryNavigation.CategoryScreen.ProductFlow
import screens.main.category.category_add.AddCategoryScreen
import screens.main.category.category_feed.CategoryFeedScreen

@Composable
internal fun FeedFlow(component: CategoryNavigation, rootModifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        animation = stackAnimation(
            fade() + scale(
                animationSpec = tween(durationMillis = 400),
                frontFactor = 0.95F,
                backFactor = 1.15F
            )
        )
    ) {
        when (val instance = it.instance) {
            is CategoryFeed -> CategoryFeedScreen(instance.component, rootModifier)
            is AddCategory -> AddCategoryScreen(instance.component, rootModifier)
            is ProductFlow -> ProductFlow(instance.component, rootModifier)
        }
    }
}
