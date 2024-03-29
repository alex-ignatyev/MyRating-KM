package screens.main.product.product_feed

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.ProductRepository
import kotlinx.coroutines.launch
import model.data.product.request.DeleteProductRequest
import model.domain.Product
import org.koin.core.component.inject
import screens.main.product.DefaultProductNavigation.ProductScreenConfig.ProductFeed.ProductFeedArguments
import screens.main.product.product_feed.ProductFeedAction.AddProduct
import screens.main.product.product_feed.ProductFeedAction.InitProductFeedScreen
import screens.main.product.product_feed.ProductFeedAction.OnBackClick
import screens.main.product.product_feed.ProductFeedAction.OnDeleteClick
import screens.main.product.product_feed.ProductFeedAction.OnEditClick
import screens.main.product.product_feed.ProductFeedAction.OnRepeatClick
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

@ExperimentalDecomposeApi
class DefaultProductFeedComponent(
    componentContext: ComponentContext,
    private val args: ProductFeedArguments,
    private val openAddProductScreen: (Long) -> Unit,
    private val openEditProductScreen: (Long, Product) -> Unit,
    private val returnToPreviousScreen: () -> Unit
) : ProductFeedComponent, BaseComponent<ProductFeedEffect>(componentContext) {

    private val repository: ProductRepository by inject()
    override val state = MutableValue(ProductFeedState())

    override fun doAction(action: ProductFeedAction) {
        when (action) {
            is InitProductFeedScreen -> fetchData()
            is AddProduct -> openAddProductScreen.invoke(args.categoryId)
            is OnBackClick -> returnToPreviousScreen.invoke()
            is OnRepeatClick -> fetchData()
            is OnEditClick -> openEditProductScreen.invoke(args.categoryId, action.product)
            is OnDeleteClick -> deleteProduct(action.productId)
        }
    }

    private fun fetchData() {
        state.value = state.value.copy(isLoading = true, screenTitle = args.screenTitle)
        componentScope.launch {
            repository.getProducts(args.categoryId)
                .onSuccess { response ->
                    state.value = state.value.copy(data = response, isLoading = false)
                }.onFailure {
                    state.value = state.value.copy(error = it.message, isLoading = false)
                }
        }
    }

    private fun deleteProduct(productId: Long) {
        componentScope.launch {
            repository.deleteProduct(
                DeleteProductRequest(
                    categoryId = args.categoryId,
                    productId = productId
                )
            ).onSuccess {
                fetchData()
            }.onFailure {
                state.value = state.value.copy(error = it.message)
            }
        }
    }
}

interface ProductFeedComponent {
    val state: Value<ProductFeedState>
    fun doAction(action: ProductFeedAction)
}
