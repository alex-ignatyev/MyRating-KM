package screens.main.category.category_feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.domain.Category
import screens.main.category.category_feed.CategoryFeedAction
import screens.main.category.category_feed.CategoryFeedAction.DeleteCategory
import screens.main.category.category_feed.CategoryFeedAction.OnCategoryClick
import screens.main.category.category_feed.CategoryFeedAction.OnEditCategoryClick
import screens.main.category.category_feed.CategoryFeedState
import ui.MRTheme
import ui.getIcons
import utils.clickableRipple
import utils.shake

@Composable
fun CategoryFeedSuccessView(state: CategoryFeedState, doAction: (CategoryFeedAction) -> Unit) {

    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier
                .background(MRTheme.colors.background)
                .padding(horizontal = 8.dp)
                .fillMaxSize()
        ) {
            itemsIndexed(state.data) { _, item ->
                CategoryFeedItem(item = item, isEdit = state.isEdit, doAction = doAction)
            }
        }
    }
}

@Composable
fun CategoryFeedItem(item: Category, isEdit: Boolean, modifier: Modifier = Modifier, doAction: (CategoryFeedAction) -> Unit) {
    Card(
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .size(100.dp)
            .shake(isEdit),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MRTheme.colors.background)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .clickableRipple {
                        if (isEdit) {
                            doAction.invoke(OnEditCategoryClick(item))
                        } else {
                            doAction.invoke(OnCategoryClick(item))
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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

            if (isEdit) {
                Image(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 4.dp, end = 4.dp)
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                        .clickableRipple {
                            doAction.invoke(DeleteCategory(item.id))
                        }
                )
            }
        }
    }
}
