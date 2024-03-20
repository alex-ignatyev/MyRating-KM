package navigation

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.main.product.ProductNavigation
import screens.main.product.ProductNavigation.ProductScreen.AddProduct
import screens.main.product.ProductNavigation.ProductScreen.ProductFeed
import screens.main.product.product_add.AddProductScreen
import screens.main.product.product_feed.ProductFeedScreen

@Composable
internal fun ProductFlow(component: ProductNavigation, rootModifier: Modifier = Modifier) {
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
            is ProductFeed -> ProductFeedScreen(instance.component, rootModifier)
            is AddProduct -> AddProductScreen(instance.component, rootModifier)
        }
    }
}
