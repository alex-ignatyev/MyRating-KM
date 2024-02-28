package screens.main.product.product_feed

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.my_rating.shared.strings.AppResStrings
import model.domain.Product
import screens.main.product.product_feed.ProductFeedAction.AddProduct
import screens.main.product.product_feed.ProductFeedAction.OnBackClick
import ui.MRTheme
import ui.components.MRDivider
import ui.components.MREmptyScreen
import ui.components.MRLoadingScreen
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close

@Composable
fun ProductFeedView(
    state: ProductFeedState,
    modifier: Modifier = Modifier,
    doAction: (ProductFeedAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = MRTheme.colors.background,
        topBar = {
            MRToolbar(
                title = state.screenTitle,
                backIcon = Close,
                onBackClick = {
                    doAction(OnBackClick)
                },
                onRightIconActionClick = {
                    doAction(AddProduct)
                }
            )
        }
    ) {
        when {
            state.isLoading -> MRLoadingScreen()
            state.error.isNotEmpty() -> {
                // TODO Добавить общий экран ошибки
            }
            state.data.isEmpty() -> MREmptyScreen(
                title = AppResStrings.title_products_empty,
                description = AppResStrings.text_products_cant_find,
                modifier = modifier
            )

            else -> LazyColumn {
                items(state.data) {
                    ProductItem(it)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Text(
            text = product.title,
            style = MRTheme.typography.body,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = product.rate.toString(),
            style = MRTheme.typography.body
        )
    }
}
