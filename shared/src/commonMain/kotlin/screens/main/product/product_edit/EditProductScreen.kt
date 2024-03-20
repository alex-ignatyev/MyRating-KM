package screens.main.product.product_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import screens.main.product.product_edit.EditProductAction.InitEditProductScreen

@Composable
fun EditProductScreen(component: EditProductComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    EditProductView(state, rootModifier) { action ->
        component.doAction(action)
    }

    LaunchedEffect(Unit) {
        component.doAction(InitEditProductScreen)
    }
}
