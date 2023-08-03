package screens.main.profile.tobacco_add

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.profile.tobacco_add.AddTobaccoAction.OpenCompanySheet
import screens.main.profile.tobacco_add.AddTobaccoAction.OpenLineSheet
import screens.main.profile.tobacco_add.AddTobaccoAction.ReturnToPreviousScreen
import screens.main.profile.tobacco_add.AddTobaccoEvent.ClearActions
import screens.main.profile.tobacco_add.AddTobaccoEvent.InitAddTobaccoScreen

object AddTobaccoScreen : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        BottomSheetNavigator {
            val navigator = LocalNavigator.currentOrThrow
            val bottomSheetNavigator = LocalBottomSheetNavigator.current

            ViewModel(factory = { AddTobaccoViewModel() }) { viewModel ->
                val state by viewModel.viewStates().collectAsState()
                val action by viewModel.viewActions().collectAsState(null)

                AddTobaccoView(state) { event ->
                    viewModel.obtainEvent(event)
                }

                LaunchedEffect(Unit) {
                    viewModel.obtainEvent(InitAddTobaccoScreen())
                }

                when (action) {
                    is ReturnToPreviousScreen -> navigator.pop()
                    is OpenCompanySheet -> {
                        bottomSheetNavigator.show(CompanyBottomSheet(state.companies, obtainEvent = viewModel::obtainEvent))
                        viewModel.obtainEvent(ClearActions())
                    }

                    is OpenLineSheet -> {
                        bottomSheetNavigator.show(LineBottomSheet((action as OpenLineSheet).lines, obtainEvent = viewModel::obtainEvent))
                        viewModel.obtainEvent(ClearActions())
                    }

                    null -> {}
                }
            }
        }
    }
}
