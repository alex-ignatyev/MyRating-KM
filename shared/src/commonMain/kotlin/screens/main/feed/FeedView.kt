package screens.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.navigationBars
import com.moriatsushi.insetsx.statusBars
import screens.main.feed.FeedAction.OnCategorySearch
import screens.main.feed.FeedAction.OnDataRefresh
import screens.main.feed.FeedState.Data
import screens.main.feed.FeedState.Empty
import screens.main.feed.view.FeedEmptyView
import screens.main.feed.view.FeedSuccessView
import ui.KalyanTheme
import ui.components.KalyanSearch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedView(state: FeedState, doAction: (FeedAction) -> Unit) {

    val refreshing by remember { mutableStateOf(false) }
    val refresh = rememberPullRefreshState(refreshing, {
        doAction(OnDataRefresh)
    })

    Scaffold(
        modifier = Modifier.pullRefresh(refresh)
            .windowInsetsPadding(WindowInsets.statusBars)
            .pullRefresh(refresh),
    ) {
        Box(modifier = Modifier.background(KalyanTheme.colors.background).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                KalyanSearch(
                    modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
                    onValueChange = {
                        doAction(OnCategorySearch(it))
                    }
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    when (state) {
                        is Empty -> FeedEmptyView(doAction)
                        is Data -> FeedSuccessView(state, doAction)
                        else -> throw IllegalStateException("Illegal state $state")
                    }

                    PullRefreshIndicator(refreshing, refresh, Modifier.align(Alignment.TopCenter))
                }
            }
        }
    }
}
