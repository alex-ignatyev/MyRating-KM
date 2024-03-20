package screens.main.product

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import screens.main.product.DefaultProductNavigation.ProductScreenConfig.ProductFeed.ProductFeedArguments
import screens.main.product.ProductNavigation.ProductScreen
import screens.main.product.product_add.AddProductComponent
import screens.main.product.product_add.DefaultAddProductComponent
import screens.main.product.product_feed.DefaultProductFeedComponent
import screens.main.product.product_feed.ProductFeedComponent

@ExperimentalDecomposeApi
class DefaultProductNavigation(
    private val componentContext: ComponentContext,
    args: ProductFeedArguments,
    private val returnToPreviousScreen: () -> Unit
) : ProductNavigation, ComponentContext by componentContext {

    private val navigation = StackNavigation<ProductScreenConfig>()

    override val stack: Value<ChildStack<*, ProductScreen>> =
        childStack(
            source = navigation,
            serializer = ProductScreenConfig.serializer(),
            initialConfiguration = ProductScreenConfig.ProductFeed(args),
            handleBackButton = true,
            childFactory = ::child,
        )

    override fun navigateToAddProduct(categoryId: Long) {
        navigation.pushNew(ProductScreenConfig.AddProduct(categoryId))
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    private fun child(config: ProductScreenConfig, childComponentContext: ComponentContext): ProductScreen =
        when (config) {
            is ProductScreenConfig.ProductFeed -> ProductScreen.ProductFeed(
                DefaultProductFeedComponent(
                    componentContext = childComponentContext,
                    args = ProductFeedArguments(config.args.categoryId, config.args.screenTitle),
                    openAddProductScreen = ::navigateToAddProduct,
                    returnToPreviousScreen = returnToPreviousScreen
                )
            )

            is ProductScreenConfig.AddProduct -> ProductScreen.AddProduct(
                DefaultAddProductComponent(
                    componentContext = childComponentContext,
                    categoryId = config.categoryId,
                    returnToPreviousScreen = {
                        navigation.pop()
                    }
                )
            )
        }

    @Serializable
    sealed interface ProductScreenConfig {

        @Serializable
        data class ProductFeed(
            val args: ProductFeedArguments
        ) : ProductScreenConfig {

            @Serializable
            data class ProductFeedArguments(
                val categoryId: Long,
                val screenTitle: String
            )
        }

        @Serializable
        data class AddProduct(
            val categoryId: Long
        ) : ProductScreenConfig
    }
}

interface ProductNavigation {
    val stack: Value<ChildStack<*, ProductScreen>>

    fun navigateToAddProduct(categoryId: Long)

    fun onBackClicked(toIndex: Int)

    @Serializable
    sealed class ProductScreen {
        @Serializable
        class ProductFeed(val component: ProductFeedComponent) : ProductScreen()

        @Serializable
        class AddProduct(val component: AddProductComponent) : ProductScreen()
    }
}
