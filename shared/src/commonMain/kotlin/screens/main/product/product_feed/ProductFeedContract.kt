package screens.main.product.product_feed

import com.my_rating.shared.strings.AppResStrings
import model.domain.Product

sealed interface ProductFeedAction {
    data object InitProductFeedScreen : ProductFeedAction
    data object AddProduct : ProductFeedAction
    data object OnBackClick : ProductFeedAction
}

data class ProductFeedState(
    val data: List<Product> = emptyList(),
    val screenTitle: String = AppResStrings.title_product_feed,
    val isLoading: Boolean = true,
    val error: String = ""
)

sealed class ProductFeedEffect
