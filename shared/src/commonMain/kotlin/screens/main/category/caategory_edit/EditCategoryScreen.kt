package screens.main.category.caategory_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import screens.main.category.caategory_edit.EditCategoryAction.InitEditCategoryScreen

@Composable
fun EditCategoryScreen(component: EditCategoryComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    EditCategoryView(state, rootModifier) { action ->
        component.doAction(action)
    }

    LaunchedEffect(Unit) {
        component.doAction(InitEditCategoryScreen)
    }
}
