package screens.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.images.AppResImages
import com.my_rating.shared.strings.AppResStrings
import io.github.skeptick.libres.compose.painterResource
import screens.main.feed.FeedAction.AddCategory
import screens.main.feed.FeedAction.InitFeedScreen
import screens.main.feed.FeedAction.OnErrorRefresh
import screens.main.feed.FeedState.Data
import screens.main.feed.FeedState.Empty
import screens.main.feed.FeedState.Error
import screens.main.feed.FeedState.Loading
import screens.main.feed.view.FeedEmptyView
import screens.main.feed.view.FeedLoadingView
import ui.MRTheme
import ui.components.ErrorScreen

@Composable
fun FeedScreen(component: FeedComponent, modifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    Column(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        if (state is Empty || state is Data) {
            Toolbar { component.doAction(it) }
        }

        when (state) {
            is Loading -> FeedLoadingView()
            is Empty -> FeedEmptyView(modifier)
            is Data -> FeedView((state as Data).data, modifier) { component.doAction(it) }
            is Error -> ErrorScreen(modifier) { component.doAction(OnErrorRefresh) }
        }
    }

    LaunchedEffect(Unit) {
        component.doAction(InitFeedScreen)
    }
}

@Composable
fun Toolbar(doAction: (FeedAction) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(MRTheme.colors.background)
            .padding(top = 16.dp)
    ) {
        Text(
            text = AppResStrings.title_categories,
            modifier = Modifier.align(Alignment.Center),
            style = MRTheme.typography.header
        )

        Icon(
            painter = painterResource(AppResImages.ic_add_circle),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp).clickable {
                doAction(AddCategory)
            }
        )
    }
}