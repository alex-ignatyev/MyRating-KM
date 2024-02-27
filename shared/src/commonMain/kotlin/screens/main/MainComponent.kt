package screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import screens.main.MainComponent.MainScreen
import screens.main.feed.DefaultFeedComponent
import screens.main.feed.FeedComponent

class DefaultMainComponent(
    private val componentContext: ComponentContext
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<MainScreenConfig>()

    override val stack: Value<ChildStack<*, MainScreen>> =
        childStack(
            source = navigation,
            serializer = MainScreenConfig.serializer(),
            initialConfiguration = MainScreenConfig.Feed,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: MainScreenConfig, childComponentContext: ComponentContext): MainScreen =
        when (config) {
            is MainScreenConfig.Feed -> MainScreen.Feed(DefaultFeedComponent(childComponentContext, { id ->

            }))

            else -> throw IllegalArgumentException()
        }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    sealed interface MainScreenConfig {
        @Serializable
        data object Feed : MainScreenConfig
    }
}

interface MainComponent {
    val stack: Value<ChildStack<*, MainScreen>>

    fun onBackClicked(toIndex: Int)

    @Serializable
    sealed class MainScreen {
        @Serializable
        class Feed(val component: FeedComponent) : MainScreen()
    }
}
