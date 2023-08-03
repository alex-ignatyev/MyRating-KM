package screens.main.mix.mix_create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.mix.mix_create.MixCreateAction.OpenCompanySheet
import screens.main.mix.mix_create.MixCreateAction.OpenLineSheet
import screens.main.mix.mix_create.MixCreateAction.ReturnToPreviousScreen
import screens.main.mix.mix_create.MixCreateEvent.ClearActions
import screens.main.mix.mix_create.MixCreateEvent.InitMixCreateScreen

object MixCreateScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        ViewModel(factory = { MixCreateViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            MixCreateView(state) { event ->
                viewModel.obtainEvent(event)
            }

            LaunchedEffect(Unit) {
                viewModel.obtainEvent(InitMixCreateScreen())
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
