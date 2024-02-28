package screens.main.product.product_feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun ProductFeedScreen(component: ProductFeedComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    ProductFeedView(state, modifier) { action ->
        component.doAction(action)
    }

    LaunchedEffect(Unit) {
        component.doAction(ProductFeedAction.InitProductFeedScreen)
    }
}
