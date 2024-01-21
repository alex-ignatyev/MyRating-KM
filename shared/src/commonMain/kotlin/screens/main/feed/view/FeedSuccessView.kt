package screens.main.feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my_rating.shared.images.AppResImages
import io.github.skeptick.libres.compose.painterResource
import screens.main.feed.FeedEvent.OnCategoryClick
import screens.main.feed.FeedState
import ui.KalyanTheme
import utils.mvi.Event

@Composable
fun FeedSuccessView(state: FeedState.Data, obtainEvent: (Event) -> Unit) {
    if (state.isLoading) {
        FeedLoadingView()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            itemsIndexed(state.data) { index, item ->
                FeedItem(item.title, modifier = Modifier.clickable {
                    obtainEvent.invoke(OnCategoryClick(item))
                })
            }
        }
    }
}

@Composable
fun FeedItem(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .size(100.dp)
            .background(KalyanTheme.colors.primary, RoundedCornerShape(8))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(AppResImages.ic_star),
                contentDescription = null
            )
            Text(
                text = title
            )
        }
    }
}
