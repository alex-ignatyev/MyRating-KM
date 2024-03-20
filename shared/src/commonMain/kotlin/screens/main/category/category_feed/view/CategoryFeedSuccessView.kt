package screens.main.category.category_feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import model.domain.Category
import screens.main.category.category_feed.CategoryFeedAction
import screens.main.category.category_feed.CategoryFeedAction.OnCategoryClick
import ui.MRTheme
import ui.getIcons
import utils.clickableRipple

@Composable
fun CategoryFeedSuccessView(categories: List<Category>, doAction: (CategoryFeedAction) -> Unit) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier
                .background(MRTheme.colors.background)
                .padding(horizontal = 8.dp)
                .fillMaxSize()
        ) {
            itemsIndexed(categories) { index, item ->
                CategoryFeedItem(item, doAction = doAction)
            }
        }
    }
}

@Composable
fun CategoryFeedItem(item: Category, modifier: Modifier = Modifier, doAction: (CategoryFeedAction) -> Unit) {
    Card(
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .size(100.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MRTheme.colors.background)
                .clickableRipple {
                    doAction.invoke(OnCategoryClick(item))
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = getIcons()[item.icon],
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
            Text(
                text = item.title,
                maxLines = 2,
                style = MRTheme.typography.body,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
    }
}
