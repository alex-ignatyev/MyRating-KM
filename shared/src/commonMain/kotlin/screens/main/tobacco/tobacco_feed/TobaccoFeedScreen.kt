package screens.main.tobacco.tobacco_feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.tobacco.tobacco_feed.TobaccoFeedAction.OpenTobaccoInfoScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.ClearActions
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.InitTobaccoFeedScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedEvent.OnErrorRefresh
import screens.main.tobacco.tobacco_feed.TobaccoFeedState.Error
import screens.main.tobacco.tobacco_feed.TobaccoFeedState.Loading
import screens.main.tobacco.tobacco_feed.view.TobaccoFeedLoadingView
import screens.main.tobacco.tobacco_info.TobaccoInfoScreen
import ui.components.ErrorScreen

object TobaccoFeedScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { TobaccoFeedViewModel() }) { vm ->
            val state by vm.viewStates().collectAsState()
            val action by vm.viewActions().collectAsState(null)

            when (state) {
                is Loading -> TobaccoFeedLoadingView()
                is Error -> ErrorScreen { vm.obtainEvent(OnErrorRefresh()) }
                else -> TobaccoFeedView(state) { vm.obtainEvent(it) }
            }

            LaunchedEffect(Unit) {
                vm.obtainEvent(InitTobaccoFeedScreen())
            }

            when (action) {
                is OpenTobaccoInfoScreen -> {
                    navigator.push(TobaccoInfoScreen((action as OpenTobaccoInfoScreen).tobaccoId))
                    vm.obtainEvent(ClearActions())
                }

                else -> Unit
            }
        }
    }
}
