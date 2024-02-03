package screens.main.category.category_add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import screens.main.category.category_add.AddCategoryAction.InitAddCategoryScreen

@Composable
fun AddCategoryScreen(component: AddCategoryComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    AddTobaccoView(state, modifier) { action ->
        component.doAction(action)
    }

    LaunchedEffect(Unit) {
        component.doAction(InitAddCategoryScreen)
    }

    /*when (action) {
        is ReturnToPreviousScreen -> navigator.pop()
        is OpenCompanySheet -> {
            bottomSheetNavigator.show(CompanyBottomSheet(state.companies, obtainEvent = viewModel::obtainEvent))
            viewModel.obtainEvent(ClearActions)
        }

        is OpenLineSheet -> {
            bottomSheetNavigator.show(LineBottomSheet((action as OpenLineSheet).lines, obtainEvent = viewModel::obtainEvent))
            viewModel.obtainEvent(ClearActions)
        }

        null -> {}
    }*/
}
