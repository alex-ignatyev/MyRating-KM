package screens.main.category

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
import screens.main.category.CategoryNavigation.CategoryScreen
import screens.main.category.category_add.AddCategoryComponent
import screens.main.category.category_add.DefaultAddCategoryComponent
import screens.main.category.category_feed.CategoryFeedComponent
import screens.main.category.category_feed.DefaultCategoryFeedComponent
import screens.main.product.DefaultProductNavigation
import screens.main.product.DefaultProductNavigation.ProductScreenConfig.ProductFeed.ProductFeedArguments
import screens.main.product.ProductNavigation

@ExperimentalDecomposeApi
class DefaultCategoryNavigation(
    private val componentContext: ComponentContext
) : CategoryNavigation, ComponentContext by componentContext {

    private val navigation = StackNavigation<CategoryScreenConfig>()

    override val stack: Value<ChildStack<*, CategoryScreen>> =
        childStack(
            source = navigation,
            serializer = CategoryScreenConfig.serializer(),
            initialConfiguration = CategoryScreenConfig.CategoryFeed,
            handleBackButton = true,
            childFactory = ::child,
        )

    override fun toAddCategoryScreen() {
        navigation.pushNew(CategoryScreenConfig.AddCategory)
    }

    override fun toToProductFlow(categoryId: Long, screenTitle: String) {
        navigation.pushNew(CategoryScreenConfig.ProductFlow(categoryId, screenTitle))
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    private fun child(config: CategoryScreenConfig, childComponentContext: ComponentContext): CategoryScreen =
        when (config) {
            is CategoryScreenConfig.CategoryFeed -> CategoryScreen.CategoryFeed(
                DefaultCategoryFeedComponent(
                    componentContext = childComponentContext,
                    openCategoryScreen = ::toToProductFlow,
                    openAddCategoryScreen = {
                        toAddCategoryScreen()
                    })
            )

            is CategoryScreenConfig.AddCategory -> CategoryScreen.AddCategory(
                DefaultAddCategoryComponent(
                    componentContext = childComponentContext,
                    returnToPreviousScreen = {
                        navigation.pop()
                    }
                )
            )

            is CategoryScreenConfig.ProductFlow -> CategoryScreen.ProductFlow(
                DefaultProductNavigation(
                    componentContext = childComponentContext,
                    args = ProductFeedArguments(config.categoryId, config.screenTitle),
                    returnToPreviousScreen = {
                        navigation.pop()
                    }
                )
            )
        }

    @Serializable
    sealed interface CategoryScreenConfig {

        @Serializable
        data object CategoryFeed : CategoryScreenConfig

        @Serializable
        data object AddCategory : CategoryScreenConfig

        @Serializable
        data class ProductFlow(
            val categoryId: Long,
            val screenTitle: String
        ) : CategoryScreenConfig
    }
}

interface CategoryNavigation {
    val stack: Value<ChildStack<*, CategoryScreen>>

    fun toToProductFlow(categoryId: Long, screenTitle: String)

    fun toAddCategoryScreen()

    fun onBackClicked(toIndex: Int)

    @Serializable
    sealed class CategoryScreen {
        @Serializable
        class AddCategory(val component: AddCategoryComponent) : CategoryScreen()

        @Serializable
        class CategoryFeed(val component: CategoryFeedComponent) : CategoryScreen()

        @Serializable
        class ProductFlow(val component: ProductNavigation) : CategoryScreen()
    }
}
