package screens.main.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import screens.main.feed.FeedAction.InitFeedScreen
import screens.main.feed.FeedAction.OnErrorRefresh
import screens.main.feed.FeedState.Error
import screens.main.feed.FeedState.Loading
import screens.main.feed.view.FeedLoadingView
import ui.components.ErrorScreen

@Composable
fun FeedScreen(component: FeedComponent) {

    val state by component.state.subscribeAsState()

    when (state) {
        is Loading -> FeedLoadingView()
        is Error -> ErrorScreen { component.doAction(OnErrorRefresh) }
        else -> FeedView(state) { component.doAction(it) }
    }

    LaunchedEffect(Unit) {
        component.doAction(InitFeedScreen)
    }
}
