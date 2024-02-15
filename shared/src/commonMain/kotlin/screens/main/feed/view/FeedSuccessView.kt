package screens.main.feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my_rating.shared.images.AppResImages
import io.github.skeptick.libres.compose.painterResource
import model.domain.Category
import screens.main.feed.FeedAction
import screens.main.feed.FeedAction.OnCategoryClick
import screens.main.feed.FeedAction.OnCategorySearch
import ui.MRTheme
import ui.components.MRSearch

@Composable
fun FeedSuccessView(categories: List<Category>, doAction: (FeedAction) -> Unit) {
    Column {
        MRSearch(
            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
            onValueChange = {
                doAction(OnCategorySearch(it))
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize()
        ) {
            itemsIndexed(categories) { index, item ->
                FeedItem(item.title, modifier = Modifier.clickable {
                    doAction.invoke(OnCategoryClick(item))
                })
            }
        }
    }
}

@Composable
fun FeedItem(title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .size(100.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MRTheme.colors.background),
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
