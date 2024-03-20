package screens.main.product.product_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AddProductScreen(component: AddProductComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    AddProductView(state, rootModifier) { action ->
        component.doAction(action)
    }
}
