package screens.main.product.product_feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.my_rating.shared.strings.AppResStrings
import model.domain.Product
import screens.main.product.product_feed.ProductFeedAction.AddProduct
import screens.main.product.product_feed.ProductFeedAction.OnBackClick
import screens.main.product.product_feed.ProductFeedAction.OnDeleteClick
import screens.main.product.product_feed.ProductFeedAction.OnEditClick
import screens.main.product.product_feed.ProductFeedAction.OnRepeatClick
import ui.MRTheme
import ui.components.MREmptyScreen
import ui.components.MRErrorScreen
import ui.components.MRLoadingScreen
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close
import utils.clickableRipple

@Composable
fun ProductFeedView(
    state: ProductFeedState,
    rootModifier: Modifier = Modifier,
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
                    doAction.invoke(OnBackClick)
                },
                onRightIconActionClick = {
                    doAction.invoke(AddProduct)
                }
            )
        }
    ) {
        when {
            state.isLoading -> MRLoadingScreen()
            state.error.isNotEmpty() -> MRErrorScreen(modifier = rootModifier) {
                doAction(OnRepeatClick)
            }

            state.data.isEmpty() -> MREmptyScreen(
                title = AppResStrings.title_products_empty,
                description = AppResStrings.text_products_cant_find,
                modifier = rootModifier
            )

            else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(state.data) { index, item ->
                    SwappableProductItem(
                        index = index,
                        product = item,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 16.dp, top = 16.dp)
                            .height(54.dp),
                        doAction = doAction
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwappableProductItem(index: Int, product: Product, modifier: Modifier = Modifier, doAction: (ProductFeedAction) -> Unit) {
    val density = LocalDensity.current
    val swipeOffset: Float = -(108.dp.value * density.density)
    val swipeState = rememberSwipeableState(initialValue = false)
    val anchors = mapOf(swipeOffset to true, 0f to false)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = (index + 1).toString(),
            style = MRTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 8.dp)
        )

        Card(
            colors = CardDefaults.cardColors(MRTheme.colors.background),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Box {
                Actions(
                    product = product,
                    modifier = Modifier.width(108.dp).align(Alignment.CenterEnd),
                    doAction = doAction
                )

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                swipeState.offset.value.toInt(), 0
                            )
                        }
                        .swipeable(
                            state = swipeState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.5f) },
                            orientation = Orientation.Horizontal
                        )
                        .fillMaxSize()
                        .background(MRTheme.colors.background)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(36.dp)
                            .clip(shape = CircleShape)
                            .background(getColor(product.rate))
                            .align(Companion.CenterEnd)
                    ) {
                        Text(
                            text = product.rate.toString(),
                            style = MRTheme.typography.caption,
                            color = MRTheme.colors.background,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Text(
                    text = product.title,
                    style = MRTheme.typography.caption,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}

@Composable
fun Actions(modifier: Modifier = Modifier, product: Product, doAction: (ProductFeedAction) -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(MRTheme.colors.secondaryText.copy(alpha = 0.3f))
                .fillMaxHeight()
                .weight(0.5f)
                .clickableRipple {
                    doAction.invoke(OnEditClick(product = product))
                }) {
            Image(
                imageVector = Icons.Default.Edit,
                colorFilter = ColorFilter.tint(MRTheme.colors.background),
                contentDescription = null,
                modifier = Modifier
                    .align(Companion.Center)
            )
        }
        Box(
            modifier = Modifier
                .background(MRTheme.colors.error.copy(alpha = 0.3f))
                .fillMaxHeight()
                .weight(0.5f)
                .clickableRipple {
                    doAction(OnDeleteClick(product.id))
                }) {
            Image(
                imageVector = Icons.Default.Delete,
                colorFilter = ColorFilter.tint(MRTheme.colors.background),
                contentDescription = null,
                modifier = Modifier
                    .align(Companion.Center)
            )
        }
    }
}

@Composable
private fun getColor(rate: Int): Color {
    return when (rate) {
        in 1..4 -> MRTheme.colors.error.copy(alpha = 0.8f)
        in 5..7 -> MRTheme.colors.secondaryText.copy(alpha = 0.8f)
        else -> MRTheme.colors.primary.copy(alpha = 0.8f)
    }
}
