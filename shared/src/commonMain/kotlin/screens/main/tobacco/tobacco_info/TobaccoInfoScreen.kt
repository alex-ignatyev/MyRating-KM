package screens.main.tobacco.tobacco_info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.tobacco.tobacco_info.TobaccoInfoAction.ReturnBack
import screens.main.tobacco.tobacco_info.TobaccoInfoEvent.InitTobaccoInfoScreen

data class TobaccoInfoScreen(val tobaccoId: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { TobaccoInfoViewModel() }) { viewModel ->
            val state by viewModel.viewStates().collectAsState()
            val action by viewModel.viewActions().collectAsState(null)

            TobaccoInfoView(state) {
                viewModel.obtainEvent(it)
            }

            LaunchedEffect(Unit) {
                viewModel.obtainEvent(InitTobaccoInfoScreen(tobaccoId))
            }

            when (action) {
                is ReturnBack -> navigator.pop()
                else -> {}
            }
        }
    }
}
