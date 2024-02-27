package screens.main.category.category_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AddCategoryScreen(component: AddCategoryComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    AddCategoryView(state, modifier) { action ->
        component.doAction(action)
    }
}
