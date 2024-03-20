package screens.main.category.category_feed

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
import screens.main.category.category_feed.CategoryFeedAction.OnDataRefresh
import screens.main.category.category_feed.view.CategoryFeedSuccessView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryFeedView(categories: List<Category>, modifier: Modifier = Modifier, doAction: (CategoryFeedAction) -> Unit) {

    val refreshing by remember { mutableStateOf(false) }
    val refresh = rememberPullRefreshState(refreshing, {
        doAction(OnDataRefresh)
    })

    Scaffold(
        modifier = modifier
            .pullRefresh(refresh)
    ) {
        Box(modifier = modifier) {
            CategoryFeedSuccessView(categories, doAction)
            PullRefreshIndicator(refreshing, refresh, Modifier.align(Alignment.TopCenter))
        }
    }
}
