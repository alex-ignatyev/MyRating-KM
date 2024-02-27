package screens.main.feed

import androidx.compose.foundation.layout.Box
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
import model.domain.Category
import screens.main.feed.FeedAction.OnDataRefresh
import screens.main.feed.view.FeedSuccessView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedView(categories: List<Category>, modifier: Modifier = Modifier, doAction: (FeedAction) -> Unit) {

    val refreshing by remember { mutableStateOf(false) }
    val refresh = rememberPullRefreshState(refreshing, {
        doAction(OnDataRefresh)
    })

    Scaffold(
        modifier = modifier.pullRefresh(refresh)
            .pullRefresh(refresh)
    ) {
        Box(modifier = modifier) {
            FeedSuccessView(categories, doAction)
            PullRefreshIndicator(refreshing, refresh, Modifier.align(Alignment.TopCenter))
        }
    }
}
