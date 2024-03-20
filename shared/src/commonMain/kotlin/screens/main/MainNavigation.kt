package screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import screens.main.MainComponent.MainScreen
import screens.main.category.CategoryNavigation
import screens.main.category.DefaultCategoryNavigation
import screens.main.profile.DefaultProfileComponent
import screens.main.profile.ProfileComponent

@ExperimentalDecomposeApi
class DefaultMainNavigation(
    private val componentContext: ComponentContext,
    private val openLoginScreen: () -> Unit
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<MainScreenConfig>()

    override val stack: Value<ChildStack<*, MainScreen>> =
        childStack(
            source = navigation,
            serializer = MainScreenConfig.serializer(),
            initialConfiguration = MainScreenConfig.FeedFlow,
            handleBackButton = true,
            childFactory = ::child,
        )

    override fun navigateToFeedFlow() {
        navigation.bringToFront(MainScreenConfig.FeedFlow)
    }

    override fun navigateToProfileFlow() {
        navigation.bringToFront(MainScreenConfig.ProfileFlow)
    }

    private fun child(config: MainScreenConfig, childComponentContext: ComponentContext): MainScreen =
        when (config) {
            is MainScreenConfig.FeedFlow -> MainScreen.FeedFlow(
                DefaultCategoryNavigation(
                    componentContext = childComponentContext
                )
            )

            is MainScreenConfig.ProfileFlow -> MainScreen.ProfileFlow(
                DefaultProfileComponent(
                    componentContext = childComponentContext,
                    openLoginScreen = openLoginScreen
                )
            )
        }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    sealed interface MainScreenConfig {

        @Serializable
        data object FeedFlow : MainScreenConfig

        @Serializable
        data object ProfileFlow : MainScreenConfig
    }
}

interface MainComponent {
    val stack: Value<ChildStack<*, MainScreen>>

    fun navigateToFeedFlow()
    fun navigateToProfileFlow()
    fun onBackClicked(toIndex: Int)

    @Serializable
    sealed class MainScreen {

        @Serializable
        class FeedFlow(val component: CategoryNavigation) : MainScreen()

        @Serializable
        class ProfileFlow(val component: ProfileComponent) : MainScreen()
    }
}
