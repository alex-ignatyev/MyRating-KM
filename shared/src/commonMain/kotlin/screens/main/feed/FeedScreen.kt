package screens.main.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.adeo.kviewmodel.compose.ViewModel
import screens.main.feed.FeedAction.OpenCategoryInfoScreen
import screens.main.feed.FeedEvent.ClearActions
import screens.main.feed.FeedEvent.InitFeedScreen
import screens.main.feed.FeedEvent.OnErrorRefresh
import screens.main.feed.FeedState.Error
import screens.main.feed.FeedState.Loading
import screens.main.feed.view.FeedLoadingView
import screens.main.tobacco.tobacco_info.TobaccoInfoScreen
import ui.components.ErrorScreen

object FeedScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        ViewModel(factory = { FeedViewModel() }) { vm ->
            val state by vm.viewStates().collectAsState()
            val action by vm.viewActions().collectAsState(null)

            when (state) {
                is Loading -> FeedLoadingView()
                is Error -> ErrorScreen { vm.obtainEvent(OnErrorRefresh()) }
                else -> FeedView(state) { vm.obtainEvent(it) }
            }

            LaunchedEffect(Unit) {
                vm.obtainEvent(InitFeedScreen())
            }

            when (action) {
                is OpenCategoryInfoScreen -> {
                    navigator.push(TobaccoInfoScreen((action as OpenCategoryInfoScreen).categoryId))
                    vm.obtainEvent(ClearActions())
                }

                else -> Unit
            }
        }
    }
}
